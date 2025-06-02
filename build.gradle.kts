import org.jreleaser.model.Active
import org.jreleaser.model.Http
import org.jreleaser.model.Signing.Mode
import org.jreleaser.model.api.deploy.maven.Nexus2MavenDeployer.Stage

plugins {
    kotlin("android") version "2.1.20" apply false
    kotlin("jvm") version "2.1.20" apply false
    id("com.android.application") version "8.9.2" apply false
    id("com.android.library") version "8.9.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
    kotlin("plugin.serialization") version "2.1.20" apply false
    id("com.google.devtools.ksp") version "2.1.20-1.0.32" apply false
    id("org.jreleaser") version "1.18.0"
    `maven-publish`
    signing
}


subprojects {
    if (this.name.startsWith("auto")) {
        apply(plugin = "org.jreleaser")
        val gpgPublicKey: String by project
        val gpgSecretKey: String by project
        val gpgKeyPassword: String by project
        val ossrhUsername: String by project
        val ossrhPassword: String by project
        val libGroupId: String by project
        val githubToken: String by project
        val releaseVersion: String by project

        jreleaser {
            project {
                name = this@subprojects.name
                version = releaseVersion
                authors = listOf("Roman Choryev")
            }
            gitRootSearch = true

            signing {
                active = Active.ALWAYS
                armored = true
                mode = Mode.FILE
                publicKey = gpgPublicKey
                secretKey = gpgSecretKey
                passphrase = gpgKeyPassword
            }

            release {
                github {
                    token = githubToken
                    enabled = true
                    skipRelease = true
                    skipTag = true
                }
            }

            deploy {
                maven {
                    mavenCentral {
                        create("releaese") {
                            setStage(Stage.UPLOAD.name)
                            active = Active.RELEASE
                            applyMavenCentralRules = false
                            url = "https://central.sonatype.com/api/v1/publisher"
                            username = ossrhUsername
                            password = ossrhPassword
                            authorization = Http.Authorization.BASIC
                            stagingRepositories = listOf(
                                layout.buildDirectory.dir("staging-deploy").get().toString()
                            )
                            namespace = libGroupId
                            sign = true
                            sourceJar = true
                            javadocJar = true
                            retryDelay = 10
                        }
                    }
                }
            }
        }
    }
}

tasks.register("publishAndDeploy") {
    group = "publishing"
    dependsOn(
        subprojects
            .filter { it.name.startsWith("auto-uikit") }
            .map { it.tasks.getByName("publish") }
    )

    finalizedBy(
        subprojects
            .filter { it.name.startsWith("auto-uikit") }
            .map { it.tasks.getByName("jreleaserDeploy") }
    )
}
