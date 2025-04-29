plugins {
    kotlin("android") version "2.1.20" apply false
    kotlin("jvm") version "2.1.20" apply false
    id("com.android.application") version "8.9.2" apply false
    id("com.android.library") version "8.9.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
    id("maven-publish")
}


tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

subprojects {
    apply(plugin = "maven-publish")
    val gitlabDeployToken: String by project
    publishing {
        repositories {
            maven {
                url = uri("")
                credentials(HttpHeaderCredentials::class) {
                    name = "Deploy-Token"
                    value = gitlabDeployToken
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
    }
}