package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.client.gateway.data.GatewayCommonRequest
import com.jeekim.server.jocr.client.gateway.data.GatewayKPICRequest
import com.jeekim.server.jocr.domain.model.PmsSession
import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest
import com.jeekim.server.jocr.utils.EncryptUtils
import com.jeekim.server.jocr.utils.toJsonNode
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import java.util.concurrent.ConcurrentHashMap

/**
 * Gateway 서비스
 */
@Service
class GatewayService {
    private val sessionMap = ConcurrentHashMap<String, PmsSession>()

    fun addSession(userId: String, session: PmsSession){
        sessionMap[userId] = session
    }

    fun send(userId: String, data: PrescriptionRequest){
        val pmsSession = sessionMap[userId] ?: return
        val request = if(pmsSession.isKPIC) GatewayKPICRequest.of(data).toJsonNode() else GatewayCommonRequest.of(data).toJsonNode()
        val textMessage = TextMessage(EncryptUtils.encrypt(request.toString()))
        pmsSession.session.sendMessage(textMessage)
    }

    fun checkConnection(userId: String): Boolean{
        val session = sessionMap[userId]?.session ?: return false
        return session.isOpen
    }

    fun removeSession(userId: String){
        sessionMap.remove(userId)
    }
}