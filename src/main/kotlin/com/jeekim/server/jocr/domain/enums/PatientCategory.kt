package com.jeekim.server.jocr.domain.enums

enum class PatientCategory(
    val word: String
) {
    HEALTH("의료보험"),
    MEDICAL_AID("의료급여"),
    INDUSTRIAL_ACCIDENT("산재보험"),
    CAR("자동차보험"),
    OTHER("기타");

    companion object {
        fun of(word: String?): PatientCategory {
            for (patientCategoryEnum in entries) {
                if (patientCategoryEnum.word == word) {
                    return patientCategoryEnum
                }
            }

            return HEALTH
        }
    }
}