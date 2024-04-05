package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.client.bucket.BucketAdapter
import com.jeekim.server.jocr.domain.entity.ShootCount
import com.jeekim.server.jocr.domain.entity.Substitute
import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest
import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.domain.repository.PrescriptionRepository
import com.jeekim.server.jocr.domain.repository.ShootCountRepository
import com.jeekim.server.jocr.domain.repository.SubstituteRepository
import com.jeekim.server.jocr.dto.prescription.UsageCount
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

/**
 * 처방전 관리 서비스
 * 1. 처방전 저장
 * 2. 처방전 조회
 * 3. transaction 관리
 */
@Service
class PrescriptionManageService(
    private val shootCountRepository: ShootCountRepository,
    private val prescriptionRepository: PrescriptionRepository,
    private val substituteRepository: SubstituteRepository,
    private val bucketAdapter: BucketAdapter

){
    fun createSend(userId: String, serviceType: ServiceType): ShootCount {
        return shootCountRepository.save(ShootCount(
            userId = userId,
            serviceType = serviceType,
        ))
    }

    fun successSend(send: ShootCount){
        shootCountRepository.save(send)
    }

    fun failSend(send: ShootCount, message: String){
        shootCountRepository.save(send)
    }

    fun substitute(data: PrescriptionRequest, userId: String, type: ServiceType){
        data.injectionPrescriptionContents = substituteAndChange(data.injectionPrescriptionContents, userId, type)
        data.internalPrescriptionContents = substituteAndChange(data.internalPrescriptionContents, userId, type)
    }

    private fun substituteAndChange(data: List<PrescriptionRequest.PrescriptionContentRequest>, userId: String, type: ServiceType): List<PrescriptionRequest.PrescriptionContentRequest>{
        return data.map {
            val originalValue = it.substitutionInfo?.originalValue
            if(originalValue != null){
                val substitute = Substitute(
                    userId = userId,
                    serviceType = type,
                    from = originalValue,
                    toCode = it.drugCode,
                    toName = it.drugName
                )
                substituteRepository.save(substitute)
            }
            it
        }
    }

    fun save(data: PrescriptionRequest, userId: String, type: ServiceType){
        // 처방전 저장 로직
        val prescription = data.toEntity(userId, type)
        val newUrl = bucketAdapter.changeNameOfFile(data.fileKey, prescription)
        prescription.updateLink(newUrl)
        prescriptionRepository.save(prescription)
    }

    fun getUsageCount(serviceType: ServiceType, userId: String):UsageCount{
        val yearMonth = YearMonth.now()
        val today = LocalDate.now()
        val start = yearMonth.atDay(1).atStartOfDay()
        val end = yearMonth.atEndOfMonth().atTime(23, 59, 59)
        val usages = shootCountRepository.findAllByServiceTypeAndUserIdAndModifiedAtIsBetween(serviceType, userId, start, end)
        if(usages.isEmpty()){
            return UsageCount(
                todayShot = 0,
                monthShot = 0,
                stored = 0
            )
        }
        val monthUsage = usages.size
        val todayUsage = usages.filter { it.modifiedAt.toLocalDate() == today }.size
//        val savedCount = shootCountRepository.countByServiceTypeAndUserIdAndSaved(serviceType, userId, true)
        return UsageCount(
            todayShot = todayUsage,
            monthShot = monthUsage,
            stored = 0
        )

    }
}