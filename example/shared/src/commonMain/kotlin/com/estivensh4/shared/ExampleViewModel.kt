package com.estivensh4.shared

import com.estivensh4.aws_s3.AwsS3
import kotlinx.datetime.Instant

class ExampleViewModel {


    fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expiration: Instant
    ): String? {
        val clientS3 = AwsS3.Builder()
            .accessKey("1")
            .secretKey("2")
            .setEndpoint("s3.amazonaws.com")
            .build()
        return clientS3.generatePresignedUrl(
            bucketName = bucketName,
            key = key,
            expiration = expiration
        )
    }
}