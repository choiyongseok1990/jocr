package com.jeekim.server.jocr.utils

import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.JocrException
import java.time.format.DateTimeFormatter

object CommonUtils {
    fun getFormat(contentType: String?): String {

        if(contentType == null) return "jpg"
        if(contentType.startsWith("image/")) {
            val type = contentType.split("/")[1]
            return setOf("jpg", "jpeg", "png").firstOrNull { it == type } ?: throw JocrException(ErrorCode.INVALID_IMAGE_TYPE)
        }
        return setOf("jpg", "jpeg", "png").firstOrNull { it == contentType } ?: throw JocrException(ErrorCode.INVALID_IMAGE_TYPE)
    }
    val NO_DASH_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val DEFAULT_YEAR_MONTH_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM")
    const val APP_CD = "JEE-KIM"
    const val HOMETAX = "hometax"
    const val CREFIA = "cardsales"
    const val MEDICARE = "medicare.nhis"
    const val ETAX_PURCHASE_CODE = "Z0005"
    const val CARD_SALES_CODE = "B0011"
    const val CARD_PURCHASE_CODE = "Z4010"
    const val CASH_PURCHASE_CODE = "Z4002"
    const val CASH_SALES_CODE = "Z4001"

    const val ID_LOGIN = "ID"

    const val S_SKIP = "skip"
    const val S_ERROR = "error"
    const val S_JOB_NAME = "job-name"
    const val S_FETCH = "fetch"
    const val S_DATE = "date"
    const val S_SUCCESS = "success"
    const val S_ERROR_MESSAGE = "error-message"

}