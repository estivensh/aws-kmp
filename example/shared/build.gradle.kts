import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.native.coroutines)
    alias(libs.plugins.ksp)
    id("com.codingfeline.buildkonfig") version "0.15.0"
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
        }
        pod("AWSS3", "~> 2.33.4")
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.datetime)
                implementation("io.github.estivensh4:aws-s3:0.4.0")
                api(libs.kmm.viewmodel.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.estivensh4.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
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