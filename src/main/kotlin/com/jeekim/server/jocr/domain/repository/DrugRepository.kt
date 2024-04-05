package com.jeekim.server.jocr.domain.repository

import com.jeekim.server.jocr.domain.entity.Drug
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface DrugRepository : JpaRepository<Drug, Long>, JpaSpecificationExecutor<Drug> {
}