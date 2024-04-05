package com.jeekim.server.jocr.client.kims

import com.fasterxml.jackson.databind.JsonNode
import com.jeekim.server.jocr.client.kims.data.KimsRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "kims-client",
    url = "\${kims.host}",
    configuration = [KimsClientConfiguration::class, KimsErrorDecoder::class]
)
interface KimsClient {

    @PostMapping("/RxData/Set")
    fun send(
        @RequestBody request: KimsRequest
    )
}