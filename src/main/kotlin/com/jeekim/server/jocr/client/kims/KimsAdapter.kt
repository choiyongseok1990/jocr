package com.jeekim.server.jocr.client.kims

import com.fasterxml.jackson.databind.JsonNode
import com.jeekim.server.jocr.client.kims.data.KimsRequest
import com.jeekim.server.jocr.utils.logger
import org.springframework.stereotype.Component

@Component
class KimsAdapter(
    private val kimsClient: KimsClient,
) {
    fun send(request: KimsRequest){
        kimsClient.send(request)
    }
}