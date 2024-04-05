package com.jeekim.server.jocr.client.gateway

import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.lang.Nullable
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception

class HttpHandShakeInterceptor: HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: org.springframework.web.socket.WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val servletRequest = request as ServletServerHttpRequest
        val userId = servletRequest.servletRequest.getAttribute("crewId")
        val deviceType = servletRequest.servletRequest.getAttribute("deviceType")
        attributes["userId"] = userId
        attributes["deviceType"] = deviceType
        return true
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: org.springframework.web.socket.WebSocketHandler,
        @Nullable exception: Exception?
    ) {
    }
}