package com.jeekim.server.jphappapi.service

import com.jeekim.server.jphappapi.exception.ErrorCode
import com.jeekim.server.jphappapi.exception.JphBizException
import org.springframework.stereotype.Service

@Service
class HospitalService(
    private val hospitalRepository: HospitalRepository
) {
    fun check(name: String, code: Long) {
        val hospital = hospitalRepository.findByName(name) ?: throw JphBizException(ErrorCode.HOSPITAL_NOT_FOUND)
        if(hospital.code != code) {
            throw JphBizException(ErrorCode.HOSPITAL_CODE_NOT_MATCH)
        }
        if(!hospital.allowed) {
            throw JphBizException(ErrorCode.HOSPITAL_NOT_ALLOWED)
        }
    }
}