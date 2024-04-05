package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.domain.entity.Drug
import com.jeekim.server.jocr.domain.repository.DrugRepository
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class DrugService(
        private val drugRepository: DrugRepository
) {
    fun getDrugsByDrugCode(drugCode: String):List<Drug>{
        val specification: Specification<Drug> = Specification.where(DrugSpecification.startsWithDrugCode(drugCode))
        return drugRepository.findAll(specification)
    }

    fun getDrugsByDrugName(drugName: String):List<Drug>{
        val specification: Specification<Drug> = Specification.where(DrugSpecification.startsWithDrugName(drugName))
        return drugRepository.findAll(specification)
    }
}