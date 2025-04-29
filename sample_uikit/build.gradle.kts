plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("android")
    id("com.google.devtools.ksp") version "2.1.20-1.0.32"

}

android {
    compileSdk = 35
    namespace = "com.zest.sample_uikit"

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

    buildFeatures {
        compose = true
    }

    kotlin.sourceSets.main {
        kotlin.srcDirs("src/main/kotlin")
    }

}
val composeVersion = "1.8.0"

dependencies {
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation(project(":auto-uikit"))
    "ksp"(project(":auto-uikit-processor"))
}