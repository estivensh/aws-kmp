import com.android.build.gradle.internal.tasks.factory.dependsOn

/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("io.kotest.multiplatform") version "5.8.0" apply false
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
    id("org.sonarqube") version "4.2.1.3168"
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
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.2.1.3168")
        classpath("org.jetbrains.kotlinx:kover-gradle-plugin:0.7.4")
    }
}

allprojects {
    plugins.withId("org.gradle.maven-publish") {
        group = "io.github.estivensh4"
        version = aws.versions.aws.get()
    }
}

koverReport {
    filters {
        excludes {
            classes(
                "kotlinx.kover.examples.merged.utils.*",
                "kotlinx.kover.examples.merged.subproject.utils.*"
            )
        }

    }
}

subprojects {
    apply(plugin = "io.kotest.multiplatform")
    apply(plugin = "org.jetbrains.kotlinx.kover")
}

dependencies {
    kover(projects.awsCommon)
    kover(projects.awsS3)
}

sonar {
    val excludes = listOf(
        "kotlinx.kover.examples.merged.utils.*"
    )
    properties {
        property("sonar.projectName", "AWS Kotlin Multiplatform")
        property("sonar.projectKey", "estivensh4_aws-kmp")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.exclusions", excludes)
    }
}

tasks.sonar.dependsOn("koverXmlReport")
