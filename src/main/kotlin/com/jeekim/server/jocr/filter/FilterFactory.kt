package com.jeekim.server.jocr.filter

import com.jeekim.server.jocr.exception.AuthException
import com.jeekim.server.jocr.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class FilterFactory(
    private val jphFilter: JphFilter,
    private val extraFilter: ExtraFilter
){
    fun getFilter(service: String?, path: String): AbstractFilter? {
        if(path.startsWith("/ocr") || path.startsWith("/prescription")) {
            return when (service) {
                "JPH" -> jphFilter
                else -> throw AuthException(ErrorCode.HEADER_NOT_FOUND)
            }
        }
        return extraFilter
    }
}