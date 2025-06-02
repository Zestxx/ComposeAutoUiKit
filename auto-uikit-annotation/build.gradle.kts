plugins {
    kotlin("jvm")
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
    google()
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

java {
    withJavadocJar()
    withSourcesJar()
    toolchain { languageVersion = JavaLanguageVersion.of(17) }
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

        create<MavenPublication>("mavenJava") {
            groupId = libGroupId
            artifactId = project.name
            version = releaseVersion

            from(components["java"])

            pom {
                name.set("auto-uikit-annotation")
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
