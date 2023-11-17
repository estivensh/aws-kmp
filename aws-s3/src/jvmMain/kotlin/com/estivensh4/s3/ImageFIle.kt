/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

import java.io.File
import java.net.URI

actual typealias ImageFile = ImageUri

actual fun ImageFile.toByteArray() = File(uri.path).readBytes()

class ImageUri(val uri: URI)

fun URI.toImageFile(): ImageFile {
    return ImageFile(this)
}