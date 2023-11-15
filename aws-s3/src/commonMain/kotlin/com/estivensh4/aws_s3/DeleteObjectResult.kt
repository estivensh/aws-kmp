package com.estivensh4.aws_s3

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
