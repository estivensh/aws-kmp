plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()

    gradlePluginPortal()
}

dependencies {
    api(libs.kotlinGradlePlugin)
    api(libs.androidGradlePlugin)
    api(libs.detektGradlePlugin)
}