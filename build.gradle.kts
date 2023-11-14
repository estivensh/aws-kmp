plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("io.kotest.multiplatform") version "5.8.0" apply false
}

buildscript {
    repositories {
        mavenCentral()
        google()

        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        gradlePluginPortal()
    }
    dependencies {
        classpath(":build-logic")
    }
}

allprojects {
    plugins.withId("org.gradle.maven-publish") {
        group = "io.github.estivensh4"
        version = aws.versions.aws.get()
    }
}

subprojects {
    apply(plugin = "io.kotest.multiplatform")
}
