enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
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
include(":example")
include(":example:androidapp")
include(":example:shared")
