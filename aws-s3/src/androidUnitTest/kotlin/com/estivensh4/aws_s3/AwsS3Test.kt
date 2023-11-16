/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.aws_s3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldHaveLength

class AwsS3Test : FunSpec({



    test("sam should be a three letter name") {
        "sam".shouldHaveLength(3)
    }

})