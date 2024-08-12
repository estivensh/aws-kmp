import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

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

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    androidTarget {
        publishAllLibraryVariants()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "aws-dynamo"
        }
        noPodspec()
       // pod("AWSCore")
        //pod("AWSDynamoDB")
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.aws.android.sdk.ddb)
            }
        }
        commonMain {
            dependencies {
                implementation(projects.awsCommon)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.kotlin.test)
                implementation(libs.kotest.assertions)
                implementation(libs.kotest.engine)
                implementation(libs.kotest.property)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.mockative)
                implementation(libs.truthish)
            }
        }
        jvmMain {
            dependencies {
                implementation(project.dependencies.platform("com.amazonaws:aws-java-sdk-bom:1.12.529"))
                implementation("com.amazonaws:aws-java-sdk-dynamodb")
            }
        }
    }
}

android {
    namespace = "com.estivensh4.dynamo"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
}