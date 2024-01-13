package com.jeekim.server.jphappapi.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val httpStatus: HttpStatus,
    val message: String,
    val userMessage: String? = null
) {
    //인가 관련 에러
    HOSPITAL_NOT_FOUND("0000001", HttpStatus.UNAUTHORIZED, "대상 병원 목록에 없음", null),
    HOSPITAL_CODE_NOT_MATCH("0000002", HttpStatus.UNAUTHORIZED, "대상 병원 코드가 일치하지 않음", null),
    HOSPITAL_NOT_ALLOWED("0000003", HttpStatus.UNAUTHORIZED, "대상 병원은 허용되지 않음", null),

    //알 수 없음
    UNEXPECTED("9999999", HttpStatus.INTERNAL_SERVER_ERROR, "UnexpectedError", null);
}