/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("org.gradle.maven-publish")
}

publishing {
    repositories.maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
        credentials {
            username = System.getenv("OSSH_USERNAME")
            password = System.getenv("OSSH_PASSWORD")
        }
    }

    publications.withType<MavenPublication> {
        pom {
            name.set("AWS Kotlin Multiplatform")
            description.set("aws is an extension for kotlin cross-platform currently android/iOS")
            url.set("https://github.com/estivensh4/aws-kmp")
            licenses {
                license {
                    name.set("Apache-2.0")
                    distribution.set("repo")
                    url.set("https://github.com/estivensh4/aws-kmp/blob/master/LICENSE.md")
                }
            }

            developers {
                developer {
                    id.set("estivensh4")
                    name.set("Estiven Sanchez")
                    email.set("estivensh4@gmail.com")
                }
            }

            scm {
                connection.set("scm:git:ssh://github.com/estivensh4/aws-kmp.git")
                developerConnection.set("scm:git:ssh://github.com/estivensh4/aws-kmp.git")
                url.set("https://github.com/estivensh4/aws-kmp")
            }
        }
    }
}


apply(plugin = "signing")

configure<SigningExtension> {
    val signingKey: String? = System.getenv("SIGNING_KEY")
    val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(signingTasks)
}

