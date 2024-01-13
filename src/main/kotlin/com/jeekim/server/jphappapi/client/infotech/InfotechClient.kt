package com.jeekim.server.jphappapi.client.infotech

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "infotech-client",
    url = "\${infotech.host}",
    configuration = [InfotechClientConfiguration::class]
)
interface InfotechClient {

    @PostMapping
    fun getMyDrugHistories(
        @RequestBody request: String
    ): String
}