package com.jeekim.server.jocr.dto.ocr

import com.fasterxml.jackson.annotation.JsonProperty
import com.jeekim.server.jocr.domain.enums.PatientCategory
import com.jeekim.server.jocr.domain.enums.PrescriptionCode
import com.jeekim.server.jocr.domain.enums.PrescriptionRefType
import com.jeekim.server.jocr.domain.enums.SelfPayRateCodeType
import com.jeekim.server.jocr.utils.CalendarUtils
import com.jeekim.server.jocr.utils.ParserUtils


data class KeyValue(
    val key: String,
    val confidence: Any,
    val value: Any
)
data class Table(
    @JsonProperty("column-header") val columnHeader: ColumnHeader,
    @JsonProperty("body") val tableBody: TableBody,
    @JsonProperty("key") val tableKey: String,
){
    data class ColumnHeader(
        @JsonProperty("key_code") val keys: List<String>?
    )

    data class TableBody(
        @JsonProperty("content") val contents: List<List<String>>?,
        @JsonProperty("score") val scores: List<List<Double>>?
    )
    fun isInternalTable(): Boolean {
        return tableKey == PrescriptionCode.INTERNAL_PRESCRIPTION_TABLE.code
    }
}
data class PatientCategory(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem {
    override fun initialize(value: Any?, needCheck: Boolean) {
        val valueString = ParserUtils.getStringValueWithoutWhiteSpace(value)
        val patientCategory = PatientCategory.of(valueString)

        this.value = patientCategory.word
        this.needCheck = needCheck
    }
}

data class IssuanceDate(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem {
    override fun initialize(value: Any?, needCheck: Boolean) {
        val generatedDate = CalendarUtils.checkDateFormatAndGet(ParserUtils.getStringValue(value))
        this.value = generatedDate.date
        this.needCheck = generatedDate.generated || needCheck
    }
}

data class GeneratedDate(
    val date: String,
    val generated: Boolean
)

data class IssuanceNumber(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem {
    override fun initialize(value: Any?, needCheck: Boolean) {
        val valueString = ParserUtils.getStringValue(value)?.padStart(5, '0')
        this.value = valueString
        this.needCheck = needCheck
    }
}

data class NursingInstNumber(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem

data class DoctorName(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem

data class PatientName(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem {
    override fun initialize(value: Any?, needCheck: Boolean) {
        val valueString = ParserUtils.getStringValue(value)

        this.value = ParserUtils.removeParenthesis(valueString)
        this.needCheck = needCheck
    }
}

data class PatientRrn(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem

data class MedicalInstName(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem

data class LicenseNumber(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem

data class DiseaseCodes(
    override var values: MutableList<String>? = mutableListOf(),
    override var needCheck: Boolean = true
) : CompositePrescriptionItem

data class PrescriptionRef(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem {
    override fun initialize(value: Any?, needCheck: Boolean) {
        val valueString = ParserUtils.getStringValue(value)
        this.value = PrescriptionRefType.determineType(valueString).word
        this.needCheck = needCheck
    }
}

data class SelfPayCode(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem

data class SelfPayRateCode(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem {
    override fun initialize(value: Any?, needCheck: Boolean) {
        val valueString = ParserUtils.getStringValue(value)
        this.value = SelfPayRateCodeType.fromInference(valueString)
        this.needCheck = needCheck
    }
}

data class DrugCode(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem

data class DrugName(
    override var value: String? = null,
    override var needCheck: Boolean = true
) : SinglePrescriptionItem

data class OneDose(
    override var value: String = "1",
    override var needCheck: Boolean = true
) : DosePrescriptionItem

data class DosingPerDay(
    override var value: String = "1",
    override var needCheck: Boolean = true
) : DosePrescriptionItem

data class TotalDosingDays(
    override var value: String = "1",
    override var needCheck: Boolean = true
) : DosePrescriptionItem

data class PrescriptionContent(
    var selfPayRateCode: SelfPayRateCode = SelfPayRateCode(),
    var drugCode: DrugCode = DrugCode(),
    var drugName: DrugName = DrugName(),
    var oneDose: OneDose = OneDose(),
    var dosingPerDay: DosingPerDay = DosingPerDay(),
    var totalDosingDays: TotalDosingDays = TotalDosingDays()
)

data class Bbox (
    @JsonProperty("x")
    val x: Any,
    @JsonProperty("y")
    val y: Any,
    @JsonProperty("w")
    val w: Any,
    @JsonProperty("h")
    val h: Any,
)