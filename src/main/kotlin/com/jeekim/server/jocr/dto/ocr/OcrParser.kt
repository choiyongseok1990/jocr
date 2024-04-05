package com.jeekim.server.jocr.dto.ocr

import com.jeekim.server.jocr.client.lomin.data.LominOcrQuery
import com.jeekim.server.jocr.domain.enums.PrescriptionCode
import com.jeekim.server.jocr.domain.enums.SelfPayRateCodeType
import com.jeekim.server.jocr.utils.ParserUtils
import com.jeekim.server.jocr.utils.logger
import java.util.regex.Pattern

class OcrParser(
    private val input: LominOcrQuery.OUT,
) {
    fun parse(url: String): OcrResponse {
        val ocrResponse = OcrResponse()
        ocrResponse.fileKey = url
        input.prediction.keyValues.forEach { keyValue -> insertGeneralContents(ocrResponse, keyValue) }
        input.prediction.tables.forEach { table -> insertPrescriptionContents(ocrResponse, table) }
        return ocrResponse
    }

    private fun insertPrescriptionContents(ocrResponse: OcrResponse, table: Table) {
        val prescriptionContents = mutableListOf<PrescriptionContent>()
        val keys: List<String>? = table.columnHeader.keys
        val contents: List<List<String>>? = table.tableBody.contents
        val confidences: List<List<Double>>? = table.tableBody.scores
        if (keys == null) {
            return
        }
        try {
            for (rowIndex in contents!!.indices) {
                prescriptionContents.add(fillInPrescriptionContents(rowIndex, keys, contents, confidences!!))
            }
        } catch (e: Exception) {
            logger().error("Error while inserting prescription contents", e)
        }
        setOcrResponsePrescriptionContents(ocrResponse, prescriptionContents, table)
    }

    private fun setOcrResponsePrescriptionContents(
        ocrResponse: OcrResponse,
        prescriptionContents: MutableList<PrescriptionContent>,
        table: Table
    ) {
        if (table.isInternalTable()) {
            ocrResponse.internalPrescriptionContents = prescriptionContents
        } else {
            ocrResponse.injectionPrescriptionContents = prescriptionContents
        }
    }

    private fun fillInPrescriptionContents(
        rowIndex: Int,
        keys: List<String>,
        contents: List<List<String>>,
        confidences: List<List<Double>>
    ): PrescriptionContent {
        val prescriptionContent = PrescriptionContent()

        for (keyIndex in keys.indices) {
            val key = keys[keyIndex]
            val value = contents[rowIndex][keyIndex]
            val needCheck: Boolean = confidences[rowIndex][keyIndex] < NEED_CHECK_CONFIDENCE_THRESHOLD
            when (PrescriptionCode.of(key)) {
                PrescriptionCode.INTERNAL_DRUG_NAME, PrescriptionCode.INJECTION_DRUG_NAME -> prescriptionContent.drugName.initialize(
                    value,
                    needCheck
                )

                PrescriptionCode.INTERNAL_ONE_DOSE, PrescriptionCode.INJECTION_ONE_DOSE -> prescriptionContent.oneDose.initialize(
                    value,
                    needCheck
                )

                PrescriptionCode.INTERNAL_DOSING_PER_DAY, PrescriptionCode.INJECTION_DOSING_PER_DAY -> prescriptionContent.dosingPerDay.initialize(
                    value,
                    needCheck
                )

                PrescriptionCode.INTERNAL_TOTAL_DOSING_DAYS, PrescriptionCode.INJECTION_TOTAL_DOSING_DAYS -> prescriptionContent.totalDosingDays.initialize(
                    value,
                    needCheck
                )

                PrescriptionCode.INTERNAL_SELF_PAY_RATE_CODE, PrescriptionCode.INJECTION_SELF_PAY_RATE_CODE -> prescriptionContent.selfPayRateCode.initialize(
                    value,
                    needCheck
                )

                else -> {}
            }
        }

        setDrugCode(prescriptionContent)
        checkAndSetSelfPayRateCode(prescriptionContent)

        return prescriptionContent
    }

    private fun checkAndSetSelfPayRateCode(prescriptionContent: PrescriptionContent) {
        val selfPayRateCode: SelfPayRateCode = prescriptionContent.selfPayRateCode
        val selfPayRateCodeString: String? = selfPayRateCode.value

        val drugNameObject: DrugName = prescriptionContent.drugName
        var drugName: String? = drugNameObject.value
        val drugNameNeedCheck: Boolean = drugNameObject.needCheck
        val defaultSelfPayRateCode = SelfPayRateCode(SelfPayRateCodeType.REIMBURSEMENT.word, drugNameNeedCheck)
        if (selfPayRateCodeString != null && drugName == null) {
            return
        }

        //로민 자기부담률구분기호 인식 X / 약품이름 X
        if (selfPayRateCodeString == null && drugName == null) {
            prescriptionContent.selfPayRateCode = defaultSelfPayRateCode
            return
        }

        if (selfPayRateCodeString == null && drugName != null) {
            drugName = setSelfPayRateCodeAndGetResultDrugName(drugName, defaultSelfPayRateCode, true)
            prescriptionContent.selfPayRateCode = defaultSelfPayRateCode
        } else if (drugName != null) {
            drugName = setSelfPayRateCodeAndGetResultDrugName(drugName, defaultSelfPayRateCode, false)
        }

        drugNameObject.value = drugName
        prescriptionContent.drugName = drugNameObject
    }

    private fun setSelfPayRateCodeAndGetResultDrugName(
        drugName: String,
        selfPayRateCode: SelfPayRateCode,
        setDrugCode: Boolean
    ): String {
        var longestSoFar = ""
        var resultDrugName = drugName

        for ((key, possibles) in SELF_PAY_RATE_KEYWORDS) {
            val possible = possibles
                .asSequence()
                .filter { drugName.contains(it) }
                .maxByOrNull(String::length)

            if (possible != null && possible.length > longestSoFar.length) {
                longestSoFar = possible
                if (setDrugCode) {
                    selfPayRateCode.value = key
                }
                resultDrugName = ParserUtils.deletePatternFromString(drugName, possible)
            }
        }

        return resultDrugName
    }

    private fun setDrugCode(prescriptionContent: PrescriptionContent) {
        val drugNameObject: DrugName = prescriptionContent.drugName
        val drugName: String? = drugNameObject.value
        val needCheck: Boolean = drugNameObject.needCheck

        if (drugName != null) {
            val drugCode = DrugCode(null, needCheck)
            val drugCodeWithBrackets: String? = checkDrugCodePattern(drugName)

            if (drugCodeWithBrackets != null) {
                drugCode.value = ParserUtils.clearBrackets(drugCodeWithBrackets)
                drugNameObject.value = ParserUtils.deletePatternFromString(drugName, drugCodeWithBrackets)
            }

            prescriptionContent.drugName = drugNameObject
            prescriptionContent.drugCode = drugCode
        }
    }

    private fun checkDrugCodePattern(drugNameString: String): String? {
        val matcher = DRUG_CODE_PATTERN.matcher(drugNameString)
        if (matcher.find()) {
            return drugNameString.substring(matcher.start(), matcher.end()).trim()
        }

        val matcherA = DRUG_CODE_PATTERN_A.matcher(drugNameString)
        if (matcherA.find()) {
            return drugNameString.substring(matcherA.start(), matcherA.end()).trim()
        }

        return null
    }

    private fun insertGeneralContents(ocrResponse: OcrResponse, keyValue: KeyValue) {
        val key: String = keyValue.key
        val value: Any = keyValue.value
        val needCheck = determineNeedCheck(key, value, keyValue.confidence)

        when (PrescriptionCode.of(key)) {
            PrescriptionCode.PATIENT_CATEGORY -> ocrResponse.patientCategory.initialize(value, needCheck)
            PrescriptionCode.ISSUANCE_DATE -> ocrResponse.issuanceDate.initialize(value, needCheck)
            PrescriptionCode.ISSUANCE_NUMBER -> ocrResponse.issuanceNumber.initialize(value, needCheck)
            PrescriptionCode.NURSING_INST_NUMBER -> ocrResponse.nursingInstNumber.initialize(value, needCheck)
            PrescriptionCode.DOCTOR_NAME -> ocrResponse.doctorName.initialize(value, needCheck)
            PrescriptionCode.PATIENT_NAME -> ocrResponse.patientName.initialize(value, needCheck)
            PrescriptionCode.PATIENT_RRN -> ocrResponse.patientRrn.initialize(value, needCheck)
            PrescriptionCode.MEDICAL_INST_NAME -> ocrResponse.medicalInstName.initialize(value, needCheck)
            PrescriptionCode.LICENSE_NUMBER -> ocrResponse.licenseNumber.initialize(value, needCheck)
            PrescriptionCode.DISEASE_CODES -> ocrResponse.diseaseCodes.initialize(value, needCheck)
            PrescriptionCode.PRESCRIPTION_REF -> ocrResponse.prescriptionRef.initialize(value, needCheck)
            PrescriptionCode.SELF_PAY_CODE -> ocrResponse.selfPayCode.initialize(value, needCheck)
            else -> {}
        }
    }


    private fun determineNeedCheck(key: String, value: Any, confidence: Any): Boolean {
        val confidenceValue = calculateConfidenceValue(confidence)

        if (PrescriptionCode.of(key).needCheckNotEssential) {
            if (ParserUtils.getStringValue(value) == null && confidenceValue == 0.0) {
                return false
            }
        }

        return confidenceValue < NEED_CHECK_CONFIDENCE_THRESHOLD
    }

    private fun calculateConfidenceValue(confidence: Any): Double {
        return when (confidence) {
            is List<*> -> {
                confidence.stream()
                    .mapToDouble { it as Double }
                    .average()
                    .orElse(0.0)
            }

            else -> confidence as Double
        }
    }

    companion object {
        const val NEED_CHECK_CONFIDENCE_THRESHOLD = 0.6
        private val DRUG_CODE_PATTERN: Pattern = Pattern.compile("[\\{\\[\\(]?[0-9]{9}[\\}\\]\\)]?")
        private val DRUG_CODE_PATTERN_A: Pattern = Pattern.compile("[\\{\\[\\(]?A[0-9]{8}[\\}\\]\\)]?")
        val SELF_PAY_RATE_KEYWORDS: Map<String, Set<String>> = mapOf(
            SelfPayRateCodeType.REIMBURSEMENT.word to setOf(
                "급여",
                "[급여]",
                "급여)",
                "(급여)",
                "(급)",
                "[급]",
                "급 ",
                "급)",
                "보 ",
                "보험)",
                "(보험)",
                "보험",
                "[보험]"
            ),
            SelfPayRateCodeType.NON_REIMBURSEMENT.word to setOf(
                "비)",
                "(비)",
                "비 ",
                "[비]",
                "비보",
                "비보)",
                "(비보)",
                "[비보]",
                "비보험)",
                "(비보험)",
                "비보험",
                "[비보험]",
                "비급여)",
                "(비급여)",
                "비급여",
                "[비급여]",
                "비급)",
                "[비급]",
                "(비급)",
                "비급",
                "W ",
                "(W)",
                "W)",
                "[W]",
                "W(비급)",
                "W(비급여)",
                "W(비보)",
                "W(비보험)",
                "W(비)"
            ),
            SelfPayRateCodeType.SELF_PAY_100.word to setOf(
                "본)",
                "(본)",
                "본 ",
                "[본]",
                "100/100",
                "(100/100)",
                "100/100)",
                "[100/100]",
                "100%",
                "(100%)",
                "100%)",
                "[100%]",
                "U ",
                "(U)",
                "U)",
                "[U]",
                "V ",
                "(V)",
                "V)",
                "[V]",
                "V(본)",
                "V(본인)",
                "V(본인부담)",
                "U(본)",
                "U(본인)",
                "U(본인부담)",
                "본인부담)",
                "(본인부담)",
                "[본인부담]",
                "본인부담"
            ),
            SelfPayRateCodeType.SELF_PAY_80.word to setOf(
                "80/100",
                "(80/100)",
                "80/100)",
                "[80/100]",
                "B ",
                "(B)",
                "B)",
                "[B]"
            ),
            SelfPayRateCodeType.SELF_PAY_50.word to setOf(
                "50/100",
                "(50/100)",
                "50/100)",
                "[50/100]",
                "A ",
                "(A)",
                "A)",
                "[A]"
            ),
            SelfPayRateCodeType.SELF_PAY_30.word to setOf("D ", "(D)", "D)", "[D]")
        )
        val SELF_PAY_RATE_KEYWORDS_WITHOUT_WHITESPACE: Map<String, Set<String>> = mapOf(
            SelfPayRateCodeType.REIMBURSEMENT.word to setOf(
                "급여", "[급여]", "급여)", "(급여)",
                "(급)", "[급]", "급", "급)",
                "보", "보험)", "(보험)", "보험", "[보험]"
            ),
            SelfPayRateCodeType.NON_REIMBURSEMENT.word to setOf(
                "비)", "(비)", "비", "[비]",
                "비보", "비보)", "(비보)", "[비보]",
                "비보험)", "(비보험)", "비보험", "[비보험]",
                "비급여)", "(비급여)", "비급여", "[비급여]",
                "비급)", "[비급]", "(비급)", "비급",
                "W", "(W)", "W)", "[W]", "W(비급)", "W(비급여)", "W(비보)", "W(비보험)", "W(비)"
            ),
            SelfPayRateCodeType.SELF_PAY_100.word to setOf(
                "본)", "(본)", "본", "[본]",
                "100/100", "(100/100)", "100/100)", "[100/100]",
                "100%", "(100%)", "100%)", "[100%]",
                "U", "(U)", "U)", "[U]",
                "V", "(V)", "V)", "[V]",
                "V(본)", "V(본인)", "V(본인부담)",
                "U(본)", "U(본인)", "U(본인부담)",
                "본인부담)", "(본인부담)", "[본인부담]", "본인부담"
            ),
            SelfPayRateCodeType.SELF_PAY_80.word to setOf(
                "80/100", "(80/100)", "80/100)", "[80/100]",
                        "B", "(B)", "B)", "[B]"
            ),
            SelfPayRateCodeType.SELF_PAY_50.word to setOf(
                "50/100","(50/100)","50/100)","[50/100]",
                        "A", "(A)", "A)", "[A]"
            ),
            SelfPayRateCodeType.SELF_PAY_30.word to setOf("D", "(D)", "D)", "[D]")
        )
    }
}