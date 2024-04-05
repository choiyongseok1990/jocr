package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.domain.entity.ShootCount
import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest
import com.jeekim.server.jocr.domain.enums.ServiceType
import org.springframework.stereotype.Service

@Service
class PrescriptionFacade(
    private val prescriptionSendService: PrescriptionSendService,
    private val prescriptionManageService: PrescriptionManageService,
) {
    /**
     * 처방전 OCR - KIMS 전송
     * 1. 전송 전 transaction 데이터 저장
     * 2. KIMS 전송
     * 3. 전송 후 transaction 데이터 업데이트(성공/실패)
     * 4. 처방전 저장
     */

    fun sendToKims(request: PrescriptionRequest, userId: String, type: ServiceType) {
        prescriptionSendService.sendToKims(request, userId)
    }

    // Web, Mobile, KIMS
    fun sendToGateway(request: PrescriptionRequest, userId: String, type: ServiceType) {
        if(type.needSubstitute()){
            prescriptionManageService.substitute(request, userId, type)
        }
        val result = runCatching { prescriptionSendService.sendToGateway(request, userId, type) }
        if(type.needSave()){
            prescriptionManageService.save(request, userId, type)
        }
    }

}