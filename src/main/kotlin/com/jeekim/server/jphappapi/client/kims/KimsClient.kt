package com.jeekim.server.jphappapi.client.kims

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "infotech-client",
    url = "\${infotech.host}",
    configuration = [KimsClientConfiguration::class]
)
interface KimsClient {

    @PostMapping
    fun sendMyDrugHistories(
        @RequestBody request: String
    ): String
}