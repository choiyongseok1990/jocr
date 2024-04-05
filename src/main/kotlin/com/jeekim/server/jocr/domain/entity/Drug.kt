package com.jeekim.server.jocr.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "drug")
data class Drug(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "drug_code")
    var drugCode: String,

    @Column(name = "drug_name")
    var drugName: String,

    @Column(name = "standard")
    var standard: Double,

    @Column(name = "unit")
    var unit: String,

    @Column(name = "upper_limit_price")
    var upperLimitPrice: Long
)