package com.jeekim.server.jocr.client.lomin

import com.jeekim.server.jocr.client.lomin.data.LominAuthQuery
import com.jeekim.server.jocr.client.lomin.data.LominOcrQuery
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "lomin-client",
    url = "\${lomin.baseUrl}",
    configuration = [LominClientConfiguration::class]
)
interface LominClient {
    @PostMapping("/auth", produces = ["application/x-www-form-urlencoded"])
    fun auth(@RequestBody request: MultiValueMap<String, String>): LominAuthQuery.OUT


    @PostMapping("/inference/docx-cls-kv")
    fun ocr(@RequestBody request: LominOcrQuery.IN, @RequestHeader("Authorization") token: String): LominOcrQuery.OUT

}