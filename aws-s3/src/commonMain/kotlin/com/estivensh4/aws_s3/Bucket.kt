package com.estivensh4.aws_s3

import kotlinx.datetime.Instant

data class Bucket(
    val name: String,
    val creationDate: Instant
)
