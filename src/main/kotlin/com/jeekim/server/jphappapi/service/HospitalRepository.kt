package com.jeekim.server.jphappapi.service

import com.jeekim.server.jphappapi.entity.Hospital
import org.springframework.data.jpa.repository.JpaRepository

interface HospitalRepository: JpaRepository<Hospital, Long> {
    fun findByName(name: String): Hospital?
}