plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("android")
    `maven-publish`
    signing
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
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
            artifactId = project.name
            version = releaseVersion

            afterEvaluate { from(components["release"]) }

            pom {
                name.set(project.name)
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
    implementation(kotlin("stdlib-jdk8"))
    api(project(":auto-uikit-core"))
    implementation("androidx.navigation:navigation-compose:2.8.9")
    implementation("androidx.activity:activity-compose:1.10.1")
}

