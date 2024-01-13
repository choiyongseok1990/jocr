package com.jeekim.server.jphappapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/check")
class CheckController {
    @GetMapping
    fun check(): String {
        return "OK"
    }
}