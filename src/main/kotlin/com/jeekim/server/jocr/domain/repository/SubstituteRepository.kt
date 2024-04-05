package com.jeekim.server.jocr.domain.repository

import com.jeekim.server.jocr.domain.entity.Substitute
import org.springframework.data.jpa.repository.JpaRepository

interface SubstituteRepository: JpaRepository<Substitute, Long> {
}