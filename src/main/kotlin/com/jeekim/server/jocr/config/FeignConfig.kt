package com.jeekim.server.jocr.config

import com.jeekim.server.jocr.client.infotech.InfoTechClient
import com.jeekim.server.jocr.client.kims.KimsClient
import com.jeekim.server.jocr.client.lomin.LominClient
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@EnableCaching
@Configuration
@ConfigurationPropertiesScan
@EnableFeignClients(
    basePackageClasses = [
        KimsClient::class,
        LominClient::class,
        InfoTechClient::class
    ]
)
class FeignConfig{
    @Bean
    fun webClient(): WebClient {
        // Omitting baseUrl
        return WebClient.builder()
            .build()
    }
}