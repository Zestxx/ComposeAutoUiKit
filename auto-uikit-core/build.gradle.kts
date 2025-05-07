plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization")
    `maven-publish`
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
        create<MavenPublication>("maven") {
            groupId = libGroupId
            artifactId = "core"
            version = releaseVersion

            from(components["java"])
        }
    }
}

val composeVersion = "1.8.0"


dependencies {
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    api("androidx.compose.ui:ui:$composeVersion")
    api("androidx.compose.foundation:foundation:$composeVersion")
    api("androidx.compose.material:material:$composeVersion")
    api("androidx.compose.runtime:runtime:$composeVersion")
    api("androidx.compose.ui:ui-tooling-preview-android:$composeVersion")
}
