plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization")
    `maven-publish`
    signing
}

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

publishing {
    publications {
        val releaseVersion: String by project
        val libGroupId: String by project
        val pomUrl: String by project
        val pomLicenseName: String by project
        val pomLicenseUrl: String by project
        val pomDescription: String by project
        val pomDeveloperName: String by project
        val pomDeveloperEmail: String by project
        val pomScmUrl: String by project
        val pomScmConnection: String by project
        val pomScmDevConnection: String by project

        create<MavenPublication>("release") {
            groupId = libGroupId
            artifactId = "auto-uikit-core"
            version = releaseVersion
            from(components["java"])

            pom {
                name.set("auto-uikit-core")
                description.set(pomDescription)
                url.set(pomUrl)
                licenses {
                    license {
                        name.set(pomLicenseName)
                        url.set(pomLicenseUrl)
                    }
                }
                developers {
                    developer {
                        name.set(pomDeveloperName)
                        email.set(pomDeveloperEmail)
                    }
                }
                scm {
                    connection.set(pomScmConnection)
                    developerConnection.set(pomScmDevConnection)
                    url.set(pomScmUrl)
                }
            }
        }
    }
    repositories {
        maven {
            setUrl(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

val composeVersion = "1.8.0"

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    api("androidx.compose.ui:ui:$composeVersion")
    api("androidx.compose.material:material:$composeVersion")
    api("androidx.compose.runtime:runtime:$composeVersion")
    api("androidx.compose.ui:ui-tooling-preview-android:$composeVersion")
}
