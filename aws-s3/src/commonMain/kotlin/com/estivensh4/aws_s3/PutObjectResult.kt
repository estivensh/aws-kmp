package com.estivensh4.aws_s3

import kotlinx.datetime.Instant

data class PutObjectResult(
    val versionId: String?,
    val eTag: String?,
    val expirationTime: Instant?,
    val contentMd5: String?,
)