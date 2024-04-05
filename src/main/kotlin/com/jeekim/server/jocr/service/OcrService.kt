package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.client.bucket.BucketAdapter
import com.jeekim.server.jocr.client.lomin.LominAdapter
import com.jeekim.server.jocr.client.lomin.data.LominOcrQuery
import com.jeekim.server.jocr.domain.entity.ShootCount
import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.domain.repository.ShootCountRepository
import com.jeekim.server.jocr.dto.ocr.OcrResponse
import com.jeekim.server.jocr.dto.ocr.OcrByBase64Request
import com.jeekim.server.jocr.dto.ocr.OcrByS3Request
import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.JocrException
import com.jeekim.server.jocr.dto.ocr.OcrParser
import com.jeekim.server.jocr.utils.CommonUtils.getFormat
import com.jeekim.server.jocr.utils.logger
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import java.io.IOException
import java.time.LocalDate
import java.util.Base64
import java.util.UUID


@Service
class OcrService(
    private val lominAdapter: LominAdapter,
    private val bucketAdapter: BucketAdapter,
    private val webClient: WebClient,
    private val shootCountRepository: ShootCountRepository
    ) {
    /**
     * 1. 파일 임시 업로드 (-> 전송 후 업로드 파일 위치 변경)
     * 2. OCR 인식
     * 3. OCR 결과 응답.
     * 4. OCR 실패시 예외 처리
     */

    fun ocrByFile(prescription: MultipartFile, id: String, service: ServiceType): OcrResponse {
        logger().info( "ocrByFile : id : {}, service : {}, fileSize: {}", id, service, prescription.size)
        val encoded = encodeFile(prescription)
        val extension = getFormat(prescription.contentType)
        val today = LocalDate.now()
        val uploadPath = "prescription/${service}/${id}/${today.year}/${today.monthValue}/${today.dayOfMonth}/${UUID.randomUUID()}.${extension}"
        val url = bucketAdapter.uploadTemp(prescription, uploadPath)
        shootCountRepository.save(ShootCount(userId = id, serviceType = service))
        return ocr(encoded, url)
    }

    fun ocrByBase64(request: OcrByBase64Request, userId: String, type: ServiceType): OcrResponse {
        return ocr(request.encodedPrescription, "")

    }

    suspend fun ocrByS3Url(request: OcrByS3Request, userId: String, type: ServiceType): OcrResponse {
        val encoded = fetchFile(request.url)
        return ocr(encoded, request.url)
    }
    private fun ocr(encoded: String, fileUrl: String): OcrResponse {
        val request = LominOcrQuery.of(encoded, fileUrl)
        val response =  runCatching { lominAdapter.ocr(request) }.getOrNull() ?: throw JocrException(ErrorCode.LOMIN_API_ERROR)
        return OcrParser(response).parse(fileUrl)
    }

    private fun encodeFile(prescription: MultipartFile): String {
        return try {
            Base64.getEncoder().encodeToString(prescription.bytes)
        } catch (e: IOException) {
            throw JocrException(ErrorCode.ENCODE_FILE_ERROR)
        }
    }
    suspend fun fetchFile(s3Url: String): String {
        val bytes = webClient.get()
            .uri(s3Url)
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .awaitSingle()

        return Base64.getEncoder().encodeToString(bytes)
    }



}