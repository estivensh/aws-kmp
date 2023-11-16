/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.aws_s3

import android.content.ContentResolver
import android.net.Uri


actual typealias ImageFile = ImageUri

actual fun ImageFile.toByteArray() = contentResolver.openInputStream(uri)?.use {
    it.readBytes()
} ?: throw IllegalStateException("Couldn't open inputStream $uri")

class ImageUri(val uri: Uri, val contentResolver: ContentResolver)

fun Uri.toImageFile(contentResolver: ContentResolver): ImageFile {
    return ImageFile(this, contentResolver)
}