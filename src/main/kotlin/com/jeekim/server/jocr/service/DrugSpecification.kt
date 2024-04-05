package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.domain.entity.Drug
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

object DrugSpecification {
    fun startsWithDrugCode(drugCode: String): Specification<Drug> {
        return Specification { root: Root<Drug>, _: CriteriaQuery<*>, builder: CriteriaBuilder ->
            builder.like(root.get("drugCode"), containingPattern(drugCode))
        }
    }

    fun startsWithDrugName(drugName: String): Specification<Drug> {
        return Specification { root: Root<Drug>, _: CriteriaQuery<*>, builder: CriteriaBuilder ->
            builder.like(root.get("drugName"), containingPattern(drugName))
        }
    }

    private fun containingPattern(keyWord: String): String {
        return "$keyWord%"
    }
}