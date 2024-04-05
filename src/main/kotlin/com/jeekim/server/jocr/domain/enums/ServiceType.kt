package com.jeekim.server.jocr.domain.enums

enum class ServiceType {
    CRJ,
    JMP,
    JPH,
    EXTERNAL;

    fun needSubstitute(): Boolean {
        return this == CRJ
    }

    fun needSave(): Boolean {
        return this == CRJ || this == JMP
    }
}