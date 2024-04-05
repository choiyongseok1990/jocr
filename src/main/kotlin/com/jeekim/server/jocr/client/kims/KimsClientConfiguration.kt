package com.jeekim.server.jocr.client.kims

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class KimsClientConfiguration {
    @Bean
    fun kimsClientRequestInterceptor(
        @Value("\${kims.username}") username: String,
    ): RequestInterceptor {
        val toEncode = "$username:"
        val encoded = "Basic " + java.util.Base64.getEncoder().encodeToString(toEncode.toByteArray())
        return RequestInterceptor { requestTemplate: RequestTemplate ->
            requestTemplate.header("Authorization", encoded)
        }
    }
}