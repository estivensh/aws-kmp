<div align="center">

  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/estivensh4/aws-kmp/main/.github/images/aws.svg">
    <img alt="Ktor logo" src="https://raw.githubusercontent.com/estivensh4/aws-kmp/main/.github/images/aws.svg" style="max-width:30%!important;margin-bottom:30px;">
  </picture>

</div>

[![Maven Central](https://img.shields.io/maven-central/v/io.github.estivensh4/aws-common)](https://mvnrepository.com/artifact/io.github.estivensh4)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.20-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

First add the dependency to your project:

```kotlin
val commonMain by getting {
    dependencies {
      implementation("io.github.estivensh4:aws-s3:0.1.0")
   }
}
```

and pod dependency

```kotlin
framework {
  baseName = "shared"
}
pod("AWSS3")
```
