package com.jeekim.server.jocr.domain.repository

import com.jeekim.server.jocr.domain.entity.Prescription
import org.springframework.data.jpa.repository.JpaRepository

interface PrescriptionRepository: JpaRepository<Prescription, Long> {
}