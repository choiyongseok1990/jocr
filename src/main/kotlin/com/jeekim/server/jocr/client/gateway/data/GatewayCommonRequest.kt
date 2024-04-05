package com.jeekim.server.jocr.client.gateway.data

import com.jeekim.server.jocr.domain.model.PrescriptionContent
import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest

data class GatewayCommonRequest (
    val patientCategory: String?,
    val patientName: String,
    val issuanceDate: String,
    val issuanceNumber: String,
    val patientRrn: String,
    val selfPayCode: String?,
    val doctorName: String,
    val medicalInstName: String,
    val nursingInstNumber: String,
    val licenseNumber: String?,
    val diseaseCodes: List<String>,
    val prescriptionRef: String?,
    val internalPrescriptionContents: List<PrescriptionContent>,
    val injectionPrescriptionContents: List<PrescriptionContent>,
    val resourceUrl: String
){
    companion object{
        fun of(request: PrescriptionRequest): GatewayCommonRequest{
            return GatewayCommonRequest(
                patientCategory = request.patientCategory,
                patientName = request.patientName,
                issuanceDate = request.issuanceDate.toString(),
                issuanceNumber = request.issuanceNumber ?: "00000",
                patientRrn = request.patientRrn,
                selfPayCode = request.selfPayCode,
                doctorName = request.doctorName ?: "",
                medicalInstName = request.medicalInstName,
                nursingInstNumber = request.nursingInstNumber,
                licenseNumber = request.licenseNumber,
                diseaseCodes = request.diseaseCodes.map { it },
                prescriptionRef = request.prescriptionRef,
                internalPrescriptionContents = request.internalPrescriptionContents.map { PrescriptionContent.of(it) },
                injectionPrescriptionContents = request.injectionPrescriptionContents.map { PrescriptionContent.of(it) },
                resourceUrl = request.fileKey
            )
        }
    }
}