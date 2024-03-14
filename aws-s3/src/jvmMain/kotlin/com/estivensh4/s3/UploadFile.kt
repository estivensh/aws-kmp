/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

import java.net.URI

actual class UploadFile constructor(
    private val uri: URI
) {
    actual fun toByteArray(): ByteArray {
        return uri.toURL().openStream()
            ?.use {
                it.readBytes()
            } ?: error("Couldn't open inputStream $uri")
    }
}

fun URI.toUploadFile(): UploadFile {
    return UploadFile(this)
}