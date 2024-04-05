package com.jeekim.server.jocr.domain.entity

import com.jeekim.server.jocr.domain.BaseTimeEntity
import com.jeekim.server.jocr.domain.converter.ServiceTypeConverter
import com.jeekim.server.jocr.domain.converter.UsageStatusConverter
import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.domain.enums.UsageStatus
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "shoot_count")
data class ShootCount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "user_id")
    val userId: String,

    @Convert(converter = ServiceTypeConverter::class)
    @Column(name = "service_type")
    val serviceType: ServiceType,
): BaseTimeEntity()