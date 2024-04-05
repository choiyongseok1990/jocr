package com.jeekim.server.jocr.domain.model

import org.springframework.web.socket.WebSocketSession

data class PmsSession (
    val isKPIC : Boolean,
    val session: WebSocketSession
)