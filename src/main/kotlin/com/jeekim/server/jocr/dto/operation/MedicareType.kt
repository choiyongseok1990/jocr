package com.jeekim.server.jocr.dto.operation

enum class MedicareType(
    val code: String,
    val description: String
) {
    NURSING("F0011", "요양 급여"),
    MEDICAL("F0012", "의료 급여"),
    DISABLED("F0013", "장애인 의료비"),
    SMOKING("F0014", "금연 치료비"),
    WELFARE("F0015", "건강 생활 유지비")
}