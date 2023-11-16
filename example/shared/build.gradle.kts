import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.native.coroutines)
    alias(libs.plugins.ksp)
    alias(libs.plugins.buildKonfig)
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
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

    kotlin.sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "shared"
            export(libs.kotlinx.datetime)
            linkerOpts.add("-lsqlite3")
        }
        pod("AWSS3", "~> 2.33.4")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.datetime)
                api(libs.kmm.viewmodel.core)
                implementation(libs.aws.s3)
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
    namespace = "com.estivensh4.shared"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

buildkonfig {

    val localProperties = Properties()
    localProperties.load(rootProject.file("local.properties").reader())

    packageName = "com.estivensh4.shared"
    exposeObjectWithName = "BuildPublicConfig"
    defaultConfigs {
        buildConfigField(STRING, "accessKey", localProperties.getProperty("AWS_ACCESS_KEY"))
        buildConfigField(STRING, "secretKey", localProperties.getProperty("AWS_SECRET_KEY"))
    }
}