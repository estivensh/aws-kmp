/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class AWSS3CommonTest {

    private lateinit var client: AWSS3
    private val bucketName = "bucket-unit-testing"

    @BeforeTest
    fun setUp() {

        val accessKey = "AKIA2D36JC4724G565BB"
        val secretKey = "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"
        client = AWSS3.builder()
            .accessKey(accessKey)
            .secretKey(secretKey)
            .setEndpoint("s3.amazonaws.com")
            .build()

    }

    @Test
    fun `verify that the bucket was created correctly`() = runTest {

        val result = client.createBucket(bucketName)

        assertContains(client.listBuckets().map { it.name }, result.name)
    }

    @Test
    fun `get image url with bucketName key and expirationInSeconds`() = runTest {

        val key = "pexels-pixabay-415829.jpg"

        val result = client.generatePresignedUrl(
            bucketName = bucketName,
            key = key,
            expirationInSeconds = 3600L
        )

        assertNotNull(result)
    }

}