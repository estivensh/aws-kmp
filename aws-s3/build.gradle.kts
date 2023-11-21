/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */
import java.net.URL

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.dokka)
    id("javadoc-stub-convention")
    id("publication-convention")
    id("detekt-convention")
}


@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()

    jvmToolchain(11)

    androidTarget {
        publishAllLibraryVariants()
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    cocoapods {
        ios.deploymentTarget = "11.0"
        framework {
            baseName = "aws-s3"
        }
        noPodspec()

        pod("AWSCore")
        pod("AWSS3")
    }
    
    sourceSets {
        /*androidNativeTest {
            dependencies {
                implementation(libs.kotest.runner)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.junit)
                implementation(libs.androidx.core)
                implementation(libs.mockito.core)
                implementation(libs.mockito.kotlin)
                implementation(libs.mockk)
                implementation(kotlin("test-junit"))
            }
        }*/
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotest.assertions)
                implementation(libs.kotest.engine)
                implementation(libs.kotest.property)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.mockative)
                implementation(libs.truthish)
            }
        }
        commonMain {
            dependencies {
                implementation(projects.awsCommon)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.aws.android.sdk.s3)
            }
        }
        jvmMain {
            dependencies {
                implementation(projects.awsCommon)
                implementation(project.dependencies.platform("com.amazonaws:aws-java-sdk-bom:1.12.529"))
                implementation("com.amazonaws:aws-java-sdk-s3")
            }
        }
    }
}

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, "io.mockative:mockative-processor:2.0.1")
        }
}

android {
    namespace = "com.estivensh4.aws_s3"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            externalDocumentationLink("https://kotlinlang.org/api/kotlinx.coroutines/")

            sourceLink {
                localDirectory.set(projectDir.resolve("src"))
                remoteUrl.set(URL("https://github.com/estivensh4/aws-kmp/tree/main/aws-s3/src"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}