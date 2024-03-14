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
        maven(url = "https://packages.jetbrains.team/maven/p/aws-sdk-kotlin/dev")
    }
}

rootProject.name = "samples"

include(":androidapp")
include(":desktopApp")
include(":shared")
include(":webApp")
include(":wearapp")
