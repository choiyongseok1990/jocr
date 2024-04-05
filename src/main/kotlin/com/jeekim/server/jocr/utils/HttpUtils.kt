package com.jeekim.server.jocr.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.JocrException
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

object HttpUtils {
    private val objectMapper = ObjectMapper()

    fun convert(dto: Any): MultiValueMap<String, String> {
        try {
            val params = LinkedMultiValueMap<String, String>()
            val map: Map<String, String> = objectMapper.convertValue(dto, object : TypeReference<Map<String, String>>() {})
            params.setAll(map)
            return params
        } catch (e: Exception) {
            throw JocrException(ErrorCode.LOMIN_API_ERROR) // Ensure you have ShotException and ErrorCode defined somewhere
        }
    }
}