package com.jeekim.server.jocr.controller

import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.dto.ocr.OcrResponse
import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.ErrorResponse
import com.jeekim.server.jocr.exception.JocrException
import com.jeekim.server.jocr.service.OcrService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


/**
 * 처방전 인식
 */
@RestController
@RequestMapping("/ocr")
@Tag(name = "OCR", description = "OCR")
class OcrController(
    private val ocrService: OcrService
) {

    @PostMapping("/file", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "처방전 인식(파일)")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "[100001] 유효하지 않은 이미지 타입 \n\n [100004] 파일 인코드 에러",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", description = "[200002] 로민 API 호출 에러",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    fun ocrByFile(
        @RequestPart prescription: MultipartFile,
        @RequestAttribute id: String,
        @RequestAttribute service: ServiceType
    ): OcrResponse {
        return ocrService.ocrByFile(prescription, id, service)
    }

//    @PostMapping("/base64", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun ocrByBase64(
//        @RequestBody request: OcrByBase64Request,
//        @RequestAttribute userId: String,
//        @RequestAttribute type: ServiceType
//    ): OcrResponse {
//        return ocrService.ocrByBase64(request, userId, type)
//    }
//
//    @PostMapping("/s3", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
//    suspend fun ocrByS3Url(
//        @RequestBody request: OcrByS3Request,
//        @RequestAttribute userId: String,
//        @RequestAttribute type: ServiceType
//    ): OcrResponse {
//        return ocrService.ocrByS3Url(request, userId, type)
//    }
}