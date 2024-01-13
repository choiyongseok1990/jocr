package com.jeekim.server.jphappapi.data

data class GetTermsResponse (
    val terms1: ImageUrl,
    val terms2: ImageUrl
){
    data class ImageUrl(
        val url: String,
        val width: Int,
        val height: Int
    )
}