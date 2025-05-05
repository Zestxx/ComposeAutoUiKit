plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.google.devtools.ksp")
}

repositories {
    mavenCentral()
    google()
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

publishing {
    publications {
        val releaseVersion: String by project
        val libGroupId: String by project
        create<MavenPublication>("maven") {
            groupId = libGroupId
            artifactId = "processor"
            version = releaseVersion

            from(components["java"])
        }
    }
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.20-1.0.32")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    api(project(":auto-uikit-core"))
}
