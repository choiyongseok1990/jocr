package com.jeekim.server.jocr.domain.enums

enum class PrescriptionRefType(
    val word: String?,
    val possibles: Set<String>
) {
    POWDER("가루약", setOf("가루")),
    COVID("코로나", setOf("코로나")),
    NONE(null, setOf());

    companion object {
        fun determineType(valueString: String?): PrescriptionRefType {
            if (valueString == null) {
                return NONE
            }

            for (prescriptionRefType in PrescriptionRefType.entries) {
                for (word in prescriptionRefType.possibles) {
                    if (valueString.contains(word)) {
                        return prescriptionRefType
                    }
                }
            }

            return NONE
        }
    }
}