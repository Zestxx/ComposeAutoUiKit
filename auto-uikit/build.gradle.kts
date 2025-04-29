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
        create<MavenPublication>("debug") {
            groupId = "com.zest.autouikit"
            artifactId = "preview"
            version = "0.1.0"

            afterEvaluate { from(components["debug"]) }
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":auto-uikit-core"))
    implementation("androidx.navigation:navigation-compose:2.8.9")
}

