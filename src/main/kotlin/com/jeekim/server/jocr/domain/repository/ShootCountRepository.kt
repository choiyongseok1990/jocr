package com.jeekim.server.jocr.domain.repository

import com.jeekim.server.jocr.domain.entity.ShootCount
import com.jeekim.server.jocr.domain.enums.ServiceType
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ShootCountRepository: JpaRepository<ShootCount, Long> {
    fun findAllByServiceTypeAndUserIdAndModifiedAtIsBetween(serviceType: ServiceType, userId: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<ShootCount>
}