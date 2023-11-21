import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.api.tasks.testing.logging.TestLogEvent

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
    alias(libs.plugins.dokka)
    id("io.kotest.multiplatform") version "5.8.0" apply false
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
    id("org.sonarqube") version "3.5.0.2730"
}

val ktlintVersion = libs.versions.ktlint.version.get()

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
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "org.jetbrains.dokka")
    apply<io.gitlab.arturbosch.detekt.DetektPlugin>()
    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        source.from(files("src/"))
        config.from(files("${project.rootDir}/detekt.yml"))
        buildUponDefaultConfig = true
        allRules = true
    }
}

dependencies {
    kover(projects.awsCommon)
    kover(projects.awsS3)
}

allprojects {

    tasks.withType<AbstractTestTask> {
        testLogging {
            showExceptions = true
            showCauses = true
            showStackTraces = true
            showStandardStreams = true
            events = setOf(
                TestLogEvent.PASSED,
                TestLogEvent.FAILED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_OUT,
                TestLogEvent.STANDARD_ERROR,
            )
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }


    tasks.withType<org.jetbrains.dokka.gradle.AbstractDokkaTask> {
        val className =
            "org.jetbrains.kotlin.gradle.targets.native.internal.CInteropMetadataDependencyTransformationTask"

        @Suppress("UNCHECKED_CAST")
        val taskClass = Class.forName(className) as Class<Task>

        parent?.subprojects?.forEach { dependsOn(it.tasks.withType(taskClass)) }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "estivensh4_aws-kmp")
        property("sonar.organization", "estivensh4-1")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "aws-s3, aws-common")
        property("sonar.test", ".")
        property("sonar.exclusions", "")
        property("sonar.test.exclusions", "")
        property("sonar.inclusions", "**/src/*Main/**/*,**/src/main/**/*,**/*.swift")
        property("sonar.test.inclusions", "**/src/*Test/**/*")
        property("sonar.c.file.suffixes", "-")
        property("sonar.cpp.file.suffixes", "-")
        property("sonar.objc.file.suffixes", "-")
        property("sonar.token", System.getenv("SONAR_TOKEN"))
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "$buildDir/reports/kover/report.xml"
        )
    }
}
tasks.sonar.dependsOn(":koverXmlReport")