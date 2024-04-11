package com.jeekim.server.jocr.utils

object Hospital {

    data class HospitalInfo(
        val name: String,
        val code: Int
    )

    val HOSPITAL_MAP = mapOf(
        "test" to HospitalInfo("테스트 계정", 12345),
        "PAKUAS" to HospitalInfo("고대안산병원", 12345),
    )
}