package com.jeekim.server.jocr.dto.operation

data class CredentialCommand (
    val crewId: Long,
    val businessNumber: String,
    val pharmacyName: String,
    val homeTax: IdPassword,
    val crefia: IdPassword,
    val cert: Cert
){
    data class IdPassword(
        val id: String,
        val password: String
    )
    data class Cert(
        val cert: String,
        val key: String,
        val password: String
    )
}