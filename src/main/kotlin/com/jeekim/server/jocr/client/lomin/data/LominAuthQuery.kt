package com.jeekim.server.jocr.client.lomin.data

import com.fasterxml.jackson.annotation.JsonProperty
import com.jeekim.server.jocr.utils.HttpUtils
import org.springframework.util.MultiValueMap

class LominAuthQuery {
    data class IN(
        @JsonProperty("email")
        val email: String,
        @JsonProperty("password")
        val password: String
    ){
        fun toForm(): MultiValueMap<String, String> {
            return HttpUtils.convert(this)
        }
    }
    data class OUT(
        @JsonProperty("access_token")
        val accessToken: String
    )
}