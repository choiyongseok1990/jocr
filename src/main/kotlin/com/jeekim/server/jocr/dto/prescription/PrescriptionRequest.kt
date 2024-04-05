package com.jeekim.server.jocr.dto.prescription

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.jeekim.server.jocr.domain.entity.Prescription
import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.domain.model.PrescriptionContent
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@JsonIgnoreProperties
data class PrescriptionRequest (
    val patientCategory: String?,
    val patientName: String,
    @field:NotNull
    val issuanceDate: LocalDate,
    val issuanceNumber: String?,
    var patientRrn: String,
    val selfPayCode: String?,
    val doctorName: String?,
    @field:NotNull
    val medicalInstName: String,
    @field:NotNull
    val nursingInstNumber: String,
    val licenseNumber: String?,
    val diseaseCodes: List<String>,
    val prescriptionRef: String?,
    var internalPrescriptionContents: List<PrescriptionContentRequest>,
    var injectionPrescriptionContents: List<PrescriptionContentRequest>,
    val expenseCalculated: Boolean = false,
    val patientExpense: Long?,
    val dispensingExpense: Long?,
    val billingExpense: Long?,
    val fileKey: String,
){

    fun clear(){
        this.patientRrn = this.patientRrn.replace("*", "")
        this.injectionPrescriptionContents = this.injectionPrescriptionContents.filter { it -> it.drugCode != null && it.drugName != null }
        this.internalPrescriptionContents = this.internalPrescriptionContents.filter { it -> it.drugCode != null && it.drugName != null }
    }
    data class PrescriptionContentRequest(
        val selfPayRateCode: String,
        val drugCode: String?,
        val drugName: String?,
        val oneDose: String,
        val dosingPerDay: String,
        val totalDosingDays: String,
        val substitutionInfo: SubstitutionInfo? = null,
        val isSubstituted: Boolean = false
    ){
        fun isValid(): Boolean {
            return drugCode != null && drugName != null
        }
        fun toContent(): PrescriptionContent {
            return PrescriptionContent(
                selfPayRateCode = selfPayRateCode,
                drugCode = drugCode,
                drugName = drugName,
                oneDose = oneDose,
                dosingPerDay = dosingPerDay,
                totalDosingDays = totalDosingDays,
                isSubstituted = isSubstituted
            )
        }
    }
    data class SubstitutionInfo(
        val substitutionId: Long,
        val originalValue: String
    )

    fun toEntity(
        userId: String,
        serviceType: ServiceType
    ): Prescription {
        val content = internalPrescriptionContents.map { it.toContent() } + injectionPrescriptionContents.map { it.toContent() }
        return Prescription(
            userId = userId,
            serviceType = serviceType,
            patientName = patientName,
            issuanceNumber = issuanceNumber ?: "00000", // 교부번호가 없으면 채워준다.
            issuanceDate = issuanceDate,
            patientRrnFirst = patientRrn.substring(0, 6),
            hospitalName = medicalInstName,
            hospitalNursingNumber = nursingInstNumber,
            content = content
        )
    }
}
