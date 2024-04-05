package com.jeekim.server.jocr.exception

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val code: String,
    val message: String,
    val status: Int
) {
    constructor(errorCode: ErrorCode) : this(
        errorCode.code,
        errorCode.userMessage ?: errorCode.message,
        errorCode.httpStatus.value()
    )

    companion object {
        fun ofUserMessage(errorCode: ErrorCode, userMessage: String): ErrorResponse {
            return ErrorResponse(
                errorCode.code,
                userMessage,
                errorCode.httpStatus.value()
            )
        }
    }
}