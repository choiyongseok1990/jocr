package com.jeekim.server.jocr.domain.model

import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest

data class PrescriptionContent(
    val selfPayRateCode: String,
    val drugCode: String?,
    val drugName: String?,
    val oneDose: String,
    val dosingPerDay: String,
    val totalDosingDays: String,
    val isSubstituted: Boolean = false
){
    companion object{
        fun of(request: PrescriptionRequest.PrescriptionContentRequest): PrescriptionContent {
            return PrescriptionContent(
                selfPayRateCode = request.selfPayRateCode,
                drugCode = request.drugCode,
                drugName = request.drugName,
                oneDose = request.oneDose,
                dosingPerDay = request.dosingPerDay,
                totalDosingDays = request.totalDosingDays,
                isSubstituted = request.isSubstituted
            )
        }
    }
}