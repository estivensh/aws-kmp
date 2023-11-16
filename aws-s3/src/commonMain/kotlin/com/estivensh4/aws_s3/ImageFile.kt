/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.aws_s3

expect open class ImageFile
expect fun ImageFile.toByteArray(): ByteArray