/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

import com.varabyte.truthish.assertThat
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class AWSS3Test {

    private lateinit var client: AWSS3

    @BeforeTest
    fun before() {
        client = AWSS3.Builder()
            .accessKey(System.getenv("AWS_ACCESS_KEY") ?: "")
            .secretKey(System.getenv("AWS_SECRET_KEY") ?: "")
            .setEndpoint("s3.amazonaws.com")
            .build()
    }

    @Test
    fun `verify that the bucket was created correctly`() = runTest {
        val bucketName = "bucket-android-test1"

        val result = client.createBucket(bucketName)

        assertThat(
            client.listBuckets().map { it.name }
        ).contains(result.name)
    }
}