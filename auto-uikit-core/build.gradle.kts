plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization") version "2.1.20"
    `maven-publish`
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.zest.autouikit"
            artifactId = "core"
            version = "0.1.0"

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
}
