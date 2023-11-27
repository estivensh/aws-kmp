/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.common.util

import com.estivensh4.common.AwsException
import kotlinx.coroutines.CompletableDeferred
import platform.Foundation.NSError

suspend inline fun <reified T> awaitResult(
    crossinline exception: (domain: NSError) -> Throwable = { Throwable("Default error") },
    function: (callback: (T?, NSError?) -> Unit) -> Unit,
): T {
    val job = CompletableDeferred<T?>()
    function { result, error ->
        if (error == null) {
            job.complete(result)
        } else {
            job.completeExceptionally(exception(error))
        }
    }
    return job.await() as T
}

suspend inline fun <T> await(function: (callback: (NSError?) -> Unit) -> T): T {
    val job = CompletableDeferred<Unit>()
    val result = function { error ->
        if (error == null) {
            job.complete(Unit)
        } else {
            job.completeExceptionally(AwsException(error.domain()))
        }
    }
    job.await()
    return result
}
