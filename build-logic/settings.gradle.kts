/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}