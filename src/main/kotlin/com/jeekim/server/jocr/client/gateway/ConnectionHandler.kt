package com.jeekim.server.jocr.client.gateway

import com.jeekim.server.jocr.domain.model.PmsSession
import com.jeekim.server.jocr.service.GatewayService
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler


@Component
class ConnectionHandler(
    private val gatewayService: GatewayService
) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = session.attributes["userId"] as String
        val deviceType = session.attributes["deviceType"] as String
        val isKPIC = deviceType == "KPIC"
        val session = PmsSession(isKPIC, session)
        gatewayService.addSession(userId, session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val userInfo = session.attributes["userInfo"] as String
        gatewayService.removeSession(userInfo)
    }

    @Throws(Exception::class)
    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        session.sendMessage(TextMessage("pong"))
    }
}
