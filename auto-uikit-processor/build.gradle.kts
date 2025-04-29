plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.google.devtools.ksp") version "2.1.20-1.0.32"
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
        create<MavenPublication>("maven") {
            groupId = "com.zest.autouikit"
            artifactId = "processor"
            version = "0.1.0"

            from(components["java"])
        }
    }
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.20-1.0.32")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    implementation(project(":auto-uikit-core"))
}
