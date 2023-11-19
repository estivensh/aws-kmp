/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3.util

import cocoapods.AWSS3.AWSS3ErrorDomain
import com.estivensh4.common.AwsException
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CompletableDeferred
import platform.Foundation.NSError

suspend inline fun <reified T> awaitResult(function: (callback: (T?, NSError?) -> Unit) -> Unit): T {
    val job = CompletableDeferred<T?>()
    function { result, error ->
        if (error == null) {
            job.complete(result)
        } else {
            job.completeExceptionally(error.toException())
        }
    }
    return job.await() as T
}

@OptIn(ExperimentalForeignApi::class)
fun NSError.toException() = when (domain) {
    AWSS3ErrorDomain -> when (code) {
        else -> AWSS3ExceptionCode.UNKNOWN
    }

    else -> AWSS3ExceptionCode.UNKNOWN
}.let { AwsException(description) }

enum class AWSS3ExceptionCode {
    UNKNOWN
}

suspend inline fun <T> await(function: (callback: (NSError?) -> Unit) -> T): T {
    val job = CompletableDeferred<Unit>()
    val result = function { error ->
        if (error == null) {
            job.complete(Unit)
        } else {
            job.completeExceptionally(error.toException())
        }
    }
    job.await()
    return result
}
