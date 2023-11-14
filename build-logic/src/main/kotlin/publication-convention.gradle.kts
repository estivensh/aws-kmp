plugins {
    id("org.gradle.maven-publish")
}

publishing {
    repositories.maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
        credentials {
            val ossrhUsername: String? by project
            val ossrhPassword: String? by project
            username = ossrhUsername
            password = ossrhPassword
        }
    }

    publications.withType<MavenPublication> {
        // Provide artifacts information requited by Maven Central
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
    val signingKey: String? by project
    val signingPassword: String? by project

    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(signingTasks)
}
