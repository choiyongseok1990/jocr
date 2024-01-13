package com.jeekim.server.jphappapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// 인증 x
@RestController
@RequestMapping
class HospitalController {
    @GetMapping("/health")
    fun healthCheck(): String {
        return "OK"
    }
}