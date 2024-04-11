package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.client.kims.KimsAdapter
import com.jeekim.server.jocr.client.kims.data.KimsRequest
import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest
import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.utils.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class PrescriptionSendService(
    private val kimsAdapter: KimsAdapter,
    private val gatewayService: GatewayService,
    @Value("\${naver.storage.endPoint}") private val baseUrl: String,
    @Value("\${naver.storage.bucketName}") private val bucketName: String
    ){
    /**
     * 파일 전송
     * 1. gateway 전송
     * 2. jmp 전송
     * 3. kims 전송
     */

    fun sendToKims(request: PrescriptionRequest, userId: String){
        // TODO:  userId로 custId를 찾는 로직 추가되어야함
        val customerId = if(userId == "test") "PAKUAS" else userId
        val kimsRequest = KimsRequest.of(request, customerId, baseUrl, bucketName)
        logger().info("sendToKims : {}", kimsRequest)
        kimsAdapter.send(kimsRequest)
    }

    fun sendToGateway(request: PrescriptionRequest, userId: String, type: ServiceType){
        gatewayService.send(userId, request)
    }
}