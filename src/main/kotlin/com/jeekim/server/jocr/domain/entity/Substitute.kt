package com.jeekim.server.jocr.domain.entity

import com.jeekim.server.jocr.domain.BaseTimeEntity
import com.jeekim.server.jocr.domain.enums.ServiceType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "substitution")
data class Substitute(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "service_type")
    @Enumerated(EnumType.STRING)
    val serviceType: ServiceType,

    @Column(name = "from")
    val from: String,

    @Column(name = "to_code")
    val toCode: String?,

    @Column(name = "to_name")
    val toName: String?,
): BaseTimeEntity()