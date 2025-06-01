plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.google.devtools.ksp")
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
            artifactId = "auto-uikit-processor"
            version = releaseVersion

            from(components["java"])

            pom {
                name.set("auto-uikit-processor")
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

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.20-1.0.32")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    api(project(":auto-uikit-core"))
}
