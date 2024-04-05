package com.jeekim.server.jocr.utils

import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.JocrException

object CommonUtils {
    fun getFormat(contentType: String?): String {

        if(contentType == null) return "jpg"
        if(contentType.startsWith("image/")) {
            val type = contentType.split("/")[1]
            return setOf("jpg", "jpeg", "png").firstOrNull { it == type } ?: throw JocrException(ErrorCode.INVALID_IMAGE_TYPE)
        }
        return setOf("jpg", "jpeg", "png").firstOrNull { it == contentType } ?: throw JocrException(ErrorCode.INVALID_IMAGE_TYPE)
    }
}