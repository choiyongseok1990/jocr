package com.jeekim.server.jocr.client.lomin

import com.jeekim.server.jocr.client.lomin.data.LominAuthQuery
import com.jeekim.server.jocr.client.lomin.data.LominOcrQuery
import com.jeekim.server.jocr.utils.HttpUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class LominAdapter(
    private val lominClient: LominClient,
    @Value("\${lomin.email}") private val email: String,
    @Value("\${lomin.password}") private val password: String
) {
    fun auth(): String {
        val request = LominAuthQuery.IN(email, password).toForm()
        val response = lominClient.auth(request)
        return response.accessToken
    }

    fun ocr(request: LominOcrQuery.IN): LominOcrQuery.OUT{
        val accessToken = auth()
        return lominClient.ocr(request, "Bearer $accessToken")
    }
}