/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

expect class AWSS3 constructor(
    accessKey: String,
    secretKey: String,
    endpoint: String
) {
    suspend fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expirationInSeconds: Long
    ): String?
    suspend fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expirationInSeconds: Long,
        method: HttpMethod
    ): String?
    suspend fun generatePresignedUrl(generatePresignedUrlRequest: GeneratePresignedUrlRequest): String?
    suspend fun createBucket(bucketName: String): Bucket
    suspend fun listBuckets(): List<Bucket>
    suspend fun deleteBucket(bucketName: String)
    suspend fun deleteObjects(bucketName: String, vararg keys: String): DeleteObjectResult
    suspend fun putObject(
        bucketName: String,
        key: String,
        imageFile: ImageFile
    ): PutObjectResult
    suspend fun listObjects(bucketName: String): ListObjectsResult

    class Builder {
        fun accessKey(accessKey: String): Builder
        fun secretKey(secretKey: String): Builder
        fun setEndpoint(endpoint: String): Builder
        fun build(): AWSS3
    }

    companion object {
        fun builder(): Builder
    }
}
