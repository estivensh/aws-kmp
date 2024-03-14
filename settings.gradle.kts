/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        gradlePluginPortal()
    }
    versionCatalogs {
        create("aws") {
            from(files("gradle/aws.versions.toml"))
        }
    }
}

rootProject.name = "aws-kmp"
includeBuild("build-logic")
include(":aws-common")
include(":aws-s3")
include(":aws-dynamo")

include(":sample:androidapp")
include(":sample:desktopApp")
include(":sample:shared")
include(":sample:webApp")
include(":sample:wearapp")
