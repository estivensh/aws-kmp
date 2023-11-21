/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

import com.estivensh4.common.AwsException
import com.varabyte.truthish.assertThat
import com.varabyte.truthish.assertThrows
import io.kotest.common.platform
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class AWSS3CommonTest {

    private lateinit var client: AWSS3
    private val bucketName = "bucket-unit-testing"
    private val key = "pexels-pixabay-415829.jpg"

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
    fun `get url of the image with generatePresignedUrl with three input parameters`() = runTest {

        val result = client.generatePresignedUrl(
            bucketName = bucketName,
            key = key,
            expirationInSeconds = 3600L
        )

        assertNotNull(result)
    }

    @Test
    fun `generate an error in generatePresignedUrl when receiving the bucketName in null`() =
        runTest {
            assertThrows<IllegalStateException> {
                client.generatePresignedUrl(
                    bucketName = null,
                    key = key,
                    expirationInSeconds = 0
                )
            }
        }

    @Test
    fun `get url of the image with generatePresignedUrl with all input parameters`() = runTest {

        val result = client.generatePresignedUrl(
            bucketName = bucketName,
            key = key,
            expirationInSeconds = 3600L,
            method = HttpMethod.GET
        )

        assertNotNull(result)
    }

    @Test
    fun `get url of the image with generatePresignedUrl with all input parameters and PUT method`() =
        runTest {

            val result = client.generatePresignedUrl(
                bucketName = bucketName,
                key = key,
                expirationInSeconds = 3600L,
                method = HttpMethod.PUT
            )

            assertNotNull(result)
        }


    @Test
    fun `get url of the image with generatePresignedUrl with all input parameters and DELETE method`() =
        runTest {

            val result = client.generatePresignedUrl(
                bucketName = bucketName,
                key = key,
                expirationInSeconds = 3600L,
                method = HttpMethod.DELETE
            )

            assertNotNull(result)
        }

    @Test
    fun `get url of the image with generatePresignedUrl with all input parameters and HEAD method`() =
        runTest {

            val result = client.generatePresignedUrl(
                bucketName = bucketName,
                key = key,
                expirationInSeconds = 3600L,
                method = HttpMethod.HEAD
            )

            assertNotNull(result)
        }


    @Test
    fun `generate an error in generatePresignedUrl when receiving the method in null`() =
        runTest {
            assertThrows<IllegalStateException> {
                client.generatePresignedUrl(
                    bucketName = bucketName,
                    key = key,
                    expirationInSeconds = 0,
                    method = null
                )
            }
        }


    @Test
    fun `get url of the image with generatePresignedUrl with request`() = runTest {

        val result = client.generatePresignedUrl(
            generatePresignedUrlRequest = GeneratePresignedUrlRequest(
                bucketName = bucketName,
                key = key,
            )
        )

        assertNotNull(result)
    }

    @Test
    fun `generate error in generatePresignedUrl when receiving null method with request parameter`() =
        runTest {
            assertThrows<IllegalStateException> {
                client.generatePresignedUrl(
                    generatePresignedUrlRequest = GeneratePresignedUrlRequest(
                        bucketName = null,
                        key = key,
                    )
                )
            }
        }

    @Test
    fun `validate error with deleteBucket if bucketName parameter is null`() = runTest {
        assertThrows<IllegalStateException> {
            client.deleteBucket(null)
        }
    }

    @Test
    fun `delete bucket with invalid bucketName`() = runTest {
        when (platform.name) {
            "JVM" -> {
                assertThrows<IllegalStateException> {
                    client.deleteBucket("")
                }
            }

            else -> {
                assertThrows<AwsException> {
                    client.deleteBucket("")
                }
            }
        }
    }

    @Test
    fun `validate error with listObjects if bucketName parameter is null`() = runTest {
        when (platform.name) {
            "JVM" -> {
                assertThrows<IllegalArgumentException> {
                    client.listObjects(null)
                }
            }

            else -> {
                assertThrows<IllegalStateException> {
                    client.listObjects(null)
                }
            }
        }
    }

    @Test
    fun `validate error with listObjects if bucketName parameter is valid`() = runTest {
        val result = client.listObjects(bucketName)

        assertNotNull(result.name)
    }

    @Test
    fun `validate error with deleteObjects if bucketName parameter is null`() = runTest {
        assertThrows<IllegalStateException> {
            client.deleteObjects(null)
        }
    }

    @Test
    fun `delete objects`() = runTest {
        if (platform.name == "JVM") {
            val result = client.deleteObjects(
                bucketName,
                key
            )

            assertThat(result.deleted).isNotEmpty()
        }
    }

    @Test
    fun `put object success`() = runTest {
        val result = client.putObject(
            bucketName = bucketName,
            key = key,
            imageFile = createImageFileForTest()
        )

        assertNotNull(result.eTag)
    }
}