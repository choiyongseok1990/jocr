package com.jeekim.server.jocr.client.lomin

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Bean

class LominClientConfiguration {
    @Bean
    fun lominClientRequestInterceptor(
    ): RequestInterceptor {
        return RequestInterceptor { requestTemplate: RequestTemplate ->
            requestTemplate.header("x-request-id", "jeekimCompany")
        }
    }
}