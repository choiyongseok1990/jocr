package com.jeekim.server.jocr.controller

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
@Hidden
class ElbController {
    @GetMapping("/health")
    fun health(): String {
        return "OK"
    }

}