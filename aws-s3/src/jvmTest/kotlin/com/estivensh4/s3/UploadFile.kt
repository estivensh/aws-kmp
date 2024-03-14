/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.s3

import io.mockk.mockk

actual fun createUploadFileForTest(): UploadFile? {
    return mockk(relaxed = true)
}