/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

data class DeleteObjectResult(
    val isRequesterCharged: Boolean,
    val deleted: List<DeleteObject>
)

data class DeleteObject(
    var key: String?,
    val versionId: String?,
    val deleteMarker: Boolean?,
    val deleteMarkerVersionId: String?
)
