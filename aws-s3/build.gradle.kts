/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    id("javadoc-stub-convention")
    id("publication-convention")
}


@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        publishAllLibraryVariants()
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

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
        androidNativeTest {
            dependencies {
                implementation(libs.kotest.runner)
                implementation("io.mockk:mockk:1.13.8")
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotest.assertions)
                implementation(libs.kotest.engine)
                implementation(libs.kotest.property)
            }
        }
        commonMain {
            dependencies {
                implementation(projects.awsCommon)
                implementation(libs.kotlinx.datetime)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        androidMain {
            dependencies {
                implementation("com.amazonaws:aws-android-sdk-s3:2.73.0")
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

android {
    namespace = "com.estivensh4.aws_s3"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    buildTypes {
        getByName("debug") {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }
}