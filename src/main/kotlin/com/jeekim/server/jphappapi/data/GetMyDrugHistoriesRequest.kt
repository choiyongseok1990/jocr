package com.jeekim.server.jphappapi.data

data class GetMyDrugHistoriesRequest (
    val name: String,
    val channel : String = "kakao",
    val phoneNumber: String,
    val rrnFirst: String,
    val rrnSecond: String,
)