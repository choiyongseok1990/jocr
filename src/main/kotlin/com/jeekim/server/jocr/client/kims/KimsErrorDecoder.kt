package com.jeekim.server.jocr.client.kims

import com.fasterxml.jackson.databind.ObjectMapper
import com.jeekim.server.jocr.client.kims.data.KimsErrorResponse
import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.JocrException
import com.jeekim.server.jocr.utils.logger
import feign.Response
import feign.codec.ErrorDecoder
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

class KimsErrorDecoder(private val objectMapper: ObjectMapper): ErrorDecoder{
    override fun decode(methodKey: String, response: Response): Exception {
        val errorResult = getResponseBodyAsString(response.body())
        return JocrException(
            ErrorCode.KIMS_API_ERROR,
                    "[${errorResult.title}] errors: ${errorResult.errors}"
        )
    }

    private fun getResponseBodyAsString(body: Response.Body): KimsErrorResponse {
        return try {
            val responseBodyMessage = IOUtils.toString(body.asReader(StandardCharsets.UTF_8))
            logger().info("KIMS 에러 응답: {}", responseBodyMessage)
            if (responseBodyMessage.isNullOrEmpty()) {
                throw JocrException(ErrorCode.KIMS_API_ERROR, "KIMS 에러 파싱 실패")
            }
            objectMapper.readValue(responseBodyMessage, KimsErrorResponse::class.java)
        } catch (e: Exception) {
            throw JocrException(ErrorCode.KIMS_API_ERROR, "KIMS 에러 파싱 실패")
        }
    }
}