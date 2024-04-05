package com.jeekim.server.jocr.domain.enums

import com.jeekim.server.jocr.dto.ocr.OcrParser

enum class SelfPayRateCodeType(
    val word: String
) {
    REIMBURSEMENT("급여"),
    NON_REIMBURSEMENT("비급여"),
    SELF_PAY_100("100% 본인"),
    SELF_PAY_90("90% 본인"),
    SELF_PAY_80("80% 본인"),
    SELF_PAY_60("60% 본인"),
    SELF_PAY_50("50% 본인"),
    SELF_PAY_30("30% 본인"),
    NULL("null");


    companion object {
        fun fromWord(selfPayRateCode: String?): SelfPayRateCodeType {
            for (selfPayRateCodes in SelfPayRateCodeType.entries) {
                if (selfPayRateCode != null && selfPayRateCodes.word == selfPayRateCode) {
                    return selfPayRateCodes
                }
            }

            return NULL
        }

        fun fromInference(valueString: String?): String? {
            if (valueString == null) {
                return null
            }

            for (key in OcrParser.SELF_PAY_RATE_KEYWORDS.keys) {
                if (OcrParser.SELF_PAY_RATE_KEYWORDS_WITHOUT_WHITESPACE[key]!!.contains(valueString)) {
                    return key
                }
            }

            return null
        }
    }
}