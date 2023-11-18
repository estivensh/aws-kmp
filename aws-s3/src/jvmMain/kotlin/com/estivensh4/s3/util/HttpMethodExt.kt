/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3.util

import com.estivensh4.s3.HttpMethod

fun HttpMethod.toAWSMethod(): com.amazonaws.HttpMethod {
    return when (this) {
        HttpMethod.GET -> com.amazonaws.HttpMethod.GET
        HttpMethod.POST -> com.amazonaws.HttpMethod.POST
        HttpMethod.PUT -> com.amazonaws.HttpMethod.PUT
        HttpMethod.DELETE -> com.amazonaws.HttpMethod.DELETE
        HttpMethod.HEAD -> com.amazonaws.HttpMethod.HEAD
        HttpMethod.PATCH -> com.amazonaws.HttpMethod.PATCH
    }
}