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
    //applyDefaultHierarchyTemplate()

    jvmToolchain(11)

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
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
            baseName = "shared"
            export(libs.kotlinx.datetime)
            //export(libs.aws.s3)
            linkerOpts.add("-lsqlite3")
        }
        pod("AWSS3", "~> 2.33.4")
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
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
        jvmMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")
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
    packageName = "com.estivensh4.shared"
    exposeObjectWithName = "BuildPublicConfig"
    defaultConfigs {
        buildConfigField(STRING, "accessKey", System.getenv("AWS_ACCESS_KEY"))
        buildConfigField(STRING, "secretKey", System.getenv("AWS_SECRET_KEY"))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile>().configureEach {
    compilerOptions.freeCompilerArgs.addAll(
        "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
        "-opt-in=kotlin.experimental.ExperimentalNativeApi"
    )
}