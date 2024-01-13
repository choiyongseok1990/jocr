package com.jeekim.server.jphappapi.client.infotech

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class InfotechClientConfiguration {
    @Bean
    open fun infotechClientRequestInterceptor(
        @Value("\${infotech.apiKey}") apiKey: String,
    ): RequestInterceptor {
        return RequestInterceptor { requestTemplate: RequestTemplate ->
            requestTemplate.header("api-cloud-key", apiKey)
        }
    }
}