plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    id("publication-convention")
}


@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

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

    cocoapods {
        ios.deploymentTarget = "11.0"
        framework {
            baseName = "aws-s3"
        }
        noPodspec()

        pod("AWSS3")
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.awsCommon)
                implementation(libs.kotlinx.datetime)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.amazonaws:aws-android-sdk-s3:2.73.0")
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
}