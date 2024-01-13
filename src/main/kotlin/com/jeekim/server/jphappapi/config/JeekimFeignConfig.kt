package com.jeekim.server.jphappapi.config

import com.jeekim.server.jphappapi.client.infotech.InfotechClient
import com.jeekim.server.jphappapi.client.kims.KimsClient
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableCaching
@Configuration
@ConfigurationPropertiesScan
@EnableFeignClients(
    basePackageClasses = [
        InfotechClient::class,
        KimsClient::class
    ]
)
open class JeekimFeignConfig