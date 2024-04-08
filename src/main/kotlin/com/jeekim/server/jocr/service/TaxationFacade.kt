package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.dto.operation.CredentialCommand
<<<<<<< Updated upstream
import com.jeekim.server.jocr.dto.operation.TaxationResult
=======
>>>>>>> Stashed changes
import org.springframework.stereotype.Service
import java.time.YearMonth

@Service
<<<<<<< Updated upstream
class TaxationFacade(
    private val taxService: TaxService,
    private val medicareService: MedicareService
) {

    fun process(request: CredentialCommand, yearMonth: YearMonth){
        val taxMap = taxService.fetch(request, yearMonth)
        val medicareMap = medicareService.fetch(request, yearMonth)
        val keys = taxMap.keys + medicareMap.keys
        keys.map { date ->
            val tax = taxMap[date] ?: emptyList()
            val medicare = medicareMap[date] ?: emptyList()
            listOf(tax, medicare)
        }
        // DB 업데이트 send (여러 월)
=======
class TaxationFacade {

    fun fetch(request: CredentialCommand, yearMonth: YearMonth){
        // homeTax fetching



>>>>>>> Stashed changes
    }
}