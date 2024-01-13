package com.jeekim.server.jphappapi.service

import com.jeekim.server.jphappapi.client.infotech.InfotechAdapter
import com.jeekim.server.jphappapi.client.kims.KimsAdapter
import org.springframework.stereotype.Service

@Service
class DrugService(
    private val kimsAdapter: KimsAdapter,
    private val infotechAdapter: InfotechAdapter,
) {

    fun getMyDrugHistories(): String {
        return infotechAdapter.getMyDrugHistories("OK")
    }

    fun sendMyDrugHistories(): String {
        return kimsAdapter.sendMyDrugHistories("OK")
    }
}