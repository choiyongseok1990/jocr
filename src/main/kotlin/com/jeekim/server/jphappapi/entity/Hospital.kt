package com.jeekim.server.jphappapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "hospital")
data class Hospital (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "code", nullable = false)
    var code: Long,

    @Column(name = "allowed", nullable = false)
    var allowed: Boolean,

)