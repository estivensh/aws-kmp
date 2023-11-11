package com.estivensh4.aws_s3

data class GeneratePresignedUrlRequest(
    val bucketName: String,
    val key: String
)
