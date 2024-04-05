package com.jeekim.server.jocr.client.gateway

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class GatewayConfig(
    private val connectionHandler: ConnectionHandler
): WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(connectionHandler, "/socket/open")
            .setAllowedOrigins("*")
            .addInterceptors(HttpHandShakeInterceptor())
    }
}