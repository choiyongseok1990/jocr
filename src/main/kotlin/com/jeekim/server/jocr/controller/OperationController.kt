package com.jeekim.server.jocr.controller

import com.jeekim.server.jocr.dto.operation.CredentialCommand
<<<<<<< Updated upstream
import com.jeekim.server.jocr.service.EdiService
import com.jeekim.server.jocr.service.TaxationFacade
import org.springframework.web.bind.annotation.PathVariable
=======
import com.jeekim.server.jocr.service.EdiFacade
import com.jeekim.server.jocr.service.TaxationFacade
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.YearMonth

@RestController
@RequestMapping("/operation")
class OperationController(
    val taxationFacade: TaxationFacade,
<<<<<<< Updated upstream
    val ediService: EdiService
) {

    @PostMapping("/{yearMonth}")
    fun execute(
        @RequestBody request: CredentialCommand,
        @PathVariable yearMonth: YearMonth
    ){
        taxationFacade.process(request, yearMonth)
        ediService.process(request, yearMonth)
    }
=======
    val ediFacade: EdiFacade
) {

    @PostMapping
    fun execute(
        @RequestBody request: CredentialCommand
    ){
        val yearMonth = getYearMonth()
        // 세무 데이터 fetching
        // 인사 데이터 fetching
        // 인사 데이터 저장
        // 세무 데이터 저장

    }

    private fun getYearMonth(): YearMonth {
        if(LocalDate.now().dayOfMonth < 15){
            return YearMonth.now().minusMonths(2)
        }
        return YearMonth.now().minusMonths(1)
    }



>>>>>>> Stashed changes
}