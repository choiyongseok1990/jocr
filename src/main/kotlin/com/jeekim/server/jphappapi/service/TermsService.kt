package com.jeekim.server.jphappapi.service

import com.amazonaws.services.s3.model.ObjectMetadata
import com.jeekim.server.jphappapi.client.bucket.BucketAdapter
import com.jeekim.server.jphappapi.data.GetTermsResponse
import org.springframework.stereotype.Service

@Service
class TermsService(
    private val bucketAdapter: BucketAdapter,
) {

    fun getTerms(): GetTermsResponse {
        val terms1: ObjectMetadata = bucketAdapter.getMetaData(TERM1)
        val terms2: ObjectMetadata = bucketAdapter.getMetaData(TERM2)

        return GetTermsResponse(
            GetTermsResponse.ImageUrl(
                url = bucketAdapter.getResourceUrl(TERM1),
                width = terms1.getUserMetaDataOf("width").toInt(),
                height = terms1.getUserMetaDataOf("height").toInt()
            ),
            GetTermsResponse.ImageUrl(
                url = bucketAdapter.getResourceUrl(TERM2),
                width = terms2.getUserMetaDataOf("width").toInt(),
                height = terms2.getUserMetaDataOf("height").toInt()
            ),
        )
    }
    companion object{
        const val TERM1 = "legalNotice/PrivacyPolicy.jpeg"
        const val TERM2 = "legalNotice/TermsOfService.jpeg"
    }
}