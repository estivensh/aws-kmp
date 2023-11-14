package com.estivensh4.aws_s3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldHaveLength

class AwsS3Test : FunSpec({



    test("sam should be a three letter name") {
        "sam".shouldHaveLength(3)
    }

})