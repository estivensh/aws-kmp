/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

data class ListObjectsResult(
    val name: String?,
    val keyCount: Int?,
    val commonPrefixes: List<String>,
    val maxKeys: Int?,
    val prefix: String?,
    val nextContinuationToken: String?,
    val delimiter: String?,
    val startAfter: String?
)
