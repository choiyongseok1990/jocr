package com.jeekim.server.jphappapi.controller

import com.jeekim.server.jphappapi.data.GetTermsResponse
import com.jeekim.server.jphappapi.service.TermsService
import com.jeekim.server.jphappapi.service.DrugService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/drug")
class DrugController(
    private val drugService: DrugService,
    private val termsService: TermsService
) {
    @GetMapping("/terms")
    fun getTerms(): GetTermsResponse {
        return termsService.getTerms()
    }
    @GetMapping("/get")
    fun getMyDrugHistories(): String {
        return "OK"
    }
    @PostMapping("/send")
    fun sendMyDrugHistories(): String {
        return "OK"
    }
}