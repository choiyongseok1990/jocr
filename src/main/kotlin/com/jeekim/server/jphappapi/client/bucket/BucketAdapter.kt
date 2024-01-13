package com.jeekim.server.jphappapi.client.bucket

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.*

class BucketAdapter(
    private val client: AmazonS3Client,
    private val bucketName: String,
    private val resourceUrlPrefix: String
) {
    fun getMetaData(fileKey: String): ObjectMetadata {
        return client.getObjectMetadata(bucketName, fileKey)
    }
    fun getResourceUrl(fileKey: String): String {
        return client.getResourceUrl(bucketName, fileKey)
    }

}