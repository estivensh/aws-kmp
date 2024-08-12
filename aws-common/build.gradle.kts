/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.URL

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.dokka)
    alias(libs.plugins.compose.compiler)
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
            baseName = "aws-common"
        }
        noPodspec()
    }
    
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.estivensh4.aws_kmp"
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
                remoteUrl.set(URL("https://github.com/estivensh4/aws-kmp/tree/main/aws-common/src"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}