/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3.util

import cocoapods.AWSS3.AWSHTTPMethod
import com.estivensh4.s3.HttpMethod
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun HttpMethod.toAWSMethod(): AWSHTTPMethod {
    return when (this) {
        HttpMethod.GET -> AWSHTTPMethod.AWSHTTPMethodGET
        HttpMethod.POST -> AWSHTTPMethod.AWSHTTPMethodPOST
        HttpMethod.PUT -> AWSHTTPMethod.AWSHTTPMethodPUT
        HttpMethod.DELETE -> AWSHTTPMethod.AWSHTTPMethodDELETE
        HttpMethod.HEAD -> AWSHTTPMethod.AWSHTTPMethodHEAD
        HttpMethod.PATCH -> AWSHTTPMethod.AWSHTTPMethodPATCH
    }
}
