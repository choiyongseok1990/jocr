package com.jeekim.server.jocr.dto.drug

import com.jeekim.server.jocr.domain.entity.Drug

data class DrugInfoResponse (
    val drugCode: String,
    val drugName: String,
){
    companion object{
        fun of(drug: Drug): DrugInfoResponse {
            return DrugInfoResponse(
                drugCode = drug.drugCode,
                drugName = drug.drugName
            )
        }
    }
}