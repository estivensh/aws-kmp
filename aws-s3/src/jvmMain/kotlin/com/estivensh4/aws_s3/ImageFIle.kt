package com.estivensh4.aws_s3

import java.io.File
import java.net.URI

actual typealias ImageFile = ImageUri

actual fun ImageFile.toByteArray() = File(uri.path).readBytes()

class ImageUri(val uri: URI)

fun URI.toImageFile(): ImageFile {
    return ImageFile(this)
}