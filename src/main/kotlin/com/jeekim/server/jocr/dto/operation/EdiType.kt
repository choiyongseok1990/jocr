package com.jeekim.server.jocr.dto.operation

enum class EdiType(
    val svcCd: String,
    val orgCd: String,
) {
    HEALTH_INSURANCE("E2222", "edi.4insure"),
    NATIONAL_PENSION("E0001", "edi.4insure"),
    EMPLOYEE_INSURANCE("E0003","edi.kcomwel"),
}