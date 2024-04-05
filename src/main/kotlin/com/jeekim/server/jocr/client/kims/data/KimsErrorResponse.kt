package com.jeekim.server.jocr.client.kims.data

import com.fasterxml.jackson.databind.JsonNode

data class KimsErrorResponse (
    val type: String,
    val title: String,
    val status: Int,
    val errors: JsonNode,
    val traceId: String
)