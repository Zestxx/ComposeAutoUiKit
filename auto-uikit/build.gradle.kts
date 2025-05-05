plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("android")
    `maven-publish`
}

android {
    compileSdk = 35
    namespace = "auto.uikit.compose"
    defaultConfig {
        minSdk = 23
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    kotlin.sourceSets.main {
        kotlin.srcDirs("src/main/kotlin")
    }

    buildFeatures {
        compose = true
    }
}

repositories {
    mavenCentral()
    google()
}

publishing {
    publications {
        val releaseVersion: String by project
        val libGroupId: String by project
        create<MavenPublication>("release") {
            groupId = libGroupId
            artifactId = "preview"
            version = releaseVersion

            afterEvaluate { from(components["release"]) }
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":auto-uikit-core"))
    implementation("androidx.navigation:navigation-compose:2.8.9")
    implementation("androidx.activity:activity-compose:1.10.1")
}

