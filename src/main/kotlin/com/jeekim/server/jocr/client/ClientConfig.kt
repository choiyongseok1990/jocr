package com.jeekim.server.jocr.client

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.jeekim.server.jocr.client.bucket.BucketAdapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientConfig {
    @Bean
    fun bucketAdapter(
        @Value("\${naver.storage.endPoint}") endPoint: String,
        @Value("\${naver.storage.access-key}") accessKey: String,
        @Value("\${naver.storage.secret-key}") secretKey: String,
        @Value("\${naver.storage.regionName}") regionName: String,
        @Value("\${naver.storage.bucketName}") bucketName: String
    ): BucketAdapter {
        val amazonS3Client: AmazonS3Client = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
            .build() as AmazonS3Client

        return BucketAdapter(amazonS3Client, bucketName)
    }
}