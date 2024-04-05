package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.client.kims.KimsAdapter
import com.jeekim.server.jocr.client.kims.data.KimsRequest
import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest
import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.utils.logger
import org.springframework.stereotype.Service

@Service
class PrescriptionSendService(
    private val kimsAdapter: KimsAdapter,
    private val gatewayService: GatewayService,
    ){
    /**
     * 파일 전송
     * 1. gateway 전송
     * 2. jmp 전송
     * 3. kims 전송
     */

    fun sendToKims(request: PrescriptionRequest, userId: String){
        // TODO:  userId로 custId를 찾는 로직 추가되어야함
        val kimsRequest = KimsRequest.of(request, "PAKUAS")
        logger().info("sendToKims : {}", kimsRequest)
        kimsAdapter.send(kimsRequest)
    }

    fun sendToGateway(request: PrescriptionRequest, userId: String, type: ServiceType){
        gatewayService.send(userId, request)
    }
}