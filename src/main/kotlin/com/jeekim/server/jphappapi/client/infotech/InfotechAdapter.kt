package com.jeekim.server.jphappapi.client.infotech


import org.springframework.stereotype.Component

@Component
class InfotechAdapter(
    private val infotechClient: InfotechClient,
) {

    fun getMyDrugHistories(string: String): String {
        return infotechClient.getMyDrugHistories(string)
    }

}