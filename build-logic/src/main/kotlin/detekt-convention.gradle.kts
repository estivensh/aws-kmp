/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    source.setFrom(
        "src/commonMain/kotlin",
        "src/androidMain/kotlin",
        "src/iosMain/kotlin",
        "src/main/kotlin"
    )
}

dependencies {
    "detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
}