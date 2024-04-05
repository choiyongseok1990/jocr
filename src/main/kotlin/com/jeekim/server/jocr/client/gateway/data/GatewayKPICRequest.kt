package com.jeekim.server.jocr.client.gateway.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.jeekim.server.jocr.domain.enums.PatientCategory
import com.jeekim.server.jocr.domain.enums.PrescriptionCode
import com.jeekim.server.jocr.domain.enums.SelfPayRateCodeType
import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest
import com.jeekim.server.jocr.utils.toNoDash

data class GatewayKPICRequest (
    @JsonProperty("Presc")
    val prescription: KPICPrescription,
    @JsonProperty("Drug")
    val contents: List<KPICPrescriptionContent>,
    @JsonProperty("ResourceUrl")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private var resourceUrl: String? = null
){
    data class KPICPrescription(
        @JsonProperty("HospNUM")
        private val hospitalNursingNumber: String,
        @JsonProperty("HospNAME")
        private val hospitalName: String,
        @JsonProperty("MprscGrantNo")
        private val issuanceNumber: String,
        @JsonProperty("PatNo")
        private val patientRrn: String,
        @JsonProperty("PatNm")
        private val patientName: String,
        @JsonProperty("InsurerType")
        private val insurerType: String,
        @JsonProperty("OfijType")
        private val ofijType: String,
        @JsonProperty("McbnfClsfcType")
        private val medicalAidType: String,
        @JsonProperty("Uni_Cd")
        private val unionCode: String,
        @JsonProperty("Uni_Nm")
        private val unionName: String,
        @JsonProperty("DstOccrDt")
        private val occurrenceDate: String,
        @JsonProperty("HospDt")
        private val issuanceDate: String,
        @JsonProperty("DrLicNo")
        private val doctorLicenseNumber: String?,
        @JsonProperty("DrName")
        private val doctorName: String?,
        @JsonProperty("Spc_Code")
        private val selfPayCode: String,
        @JsonProperty("Sick_Code")
        private val diseaseCodes: String,
        @JsonProperty("PrscRef")
        private val prescriptionReference: String,
        @JsonProperty("PrscPeriod")
        private val prescriptionPeriod: String
    ){
        companion object{
            fun of(request: PrescriptionRequest): KPICPrescription{
                return KPICPrescription(
                    hospitalNursingNumber =request.nursingInstNumber,
                    hospitalName = request.medicalInstName,
                    issuanceNumber = request.issuanceNumber ?: "00000", // TODO five digit
                    patientRrn = request.patientRrn,
                    patientName = request.patientName,
                    insurerType = PatientCategory.of(request.patientCategory).ordinal.toString(),
                    ofijType = "",
                    medicalAidType = "",
                    unionCode = "",
                    unionName = "",
                    occurrenceDate = "",
                    issuanceDate = request.issuanceDate.toNoDash(),
                    doctorLicenseNumber = request.licenseNumber,
                    doctorName = request.doctorName,
                    selfPayCode = request.selfPayCode ?: "",
                    diseaseCodes = request.diseaseCodes.joinToString("|"),
                    prescriptionReference = if(request.prescriptionRef?.contains("가루") == true) "POW" else "",
                    prescriptionPeriod = ""
                )
            }
        }
    }

    data class KPICPrescriptionContent(
        @JsonProperty("PrscType")
        private val prescriptionType: String,
        @JsonProperty("DrugCd")
        private val drugCode: String?,
        @JsonProperty("DrugNm")
        private val drugName: String?,
        @JsonProperty("DD_MQTY")
        private val oneDose: String,
        @JsonProperty("DD_FREQ")
        private val dosingPerDay: String,
        @JsonProperty("MDCN_EXEC_FREQ")
        private val totalDosingDays: String,
        @JsonProperty("InsuType")
        private val selfPayRateCode: String,
        @JsonProperty("TAKE_MEDI")
        private val takeMedicineCode: String
    ){
        companion object{
            fun of(request: PrescriptionRequest.PrescriptionContentRequest, type: String): KPICPrescriptionContent{
                return KPICPrescriptionContent(
                    prescriptionType = type,
                    drugCode = request.drugCode,
                    drugName = request.drugName,
                    oneDose = request.oneDose,
                    dosingPerDay = request.dosingPerDay,
                    totalDosingDays = request.totalDosingDays,
                    selfPayRateCode = SelfPayRateCodeType.fromWord(request.selfPayRateCode).ordinal.toString(),
                    takeMedicineCode = ""
                )
            }
        }
    }

    companion object{
        fun of(request: PrescriptionRequest): GatewayKPICRequest{
            val prescription = KPICPrescription.of(request)
            /**
             * 0: 내복
             * 2: 주사
             */
            val contents = request.internalPrescriptionContents.map { KPICPrescriptionContent.of(it, "0") } +
                    request.injectionPrescriptionContents.map { KPICPrescriptionContent.of(it, "2") }
            return GatewayKPICRequest(
                prescription = prescription,
                contents = contents,
                resourceUrl = request.fileKey
            )
        }
    }
}