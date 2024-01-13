package com.jeekim.server.jphappapi.client.kims

import org.springframework.stereotype.Component

@Component
class KimsAdapter(
    private val kimsClient: KimsClient,
) {
    fun sendMyDrugHistories(string: String): String {
        return kimsClient.sendMyDrugHistories(string)
    }
}