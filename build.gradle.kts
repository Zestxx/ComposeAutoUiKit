import java.util.Properties

plugins {
    kotlin("android") version "2.1.20" apply false
    kotlin("jvm") version "2.1.20" apply false
    id("com.android.application") version "8.9.2" apply false
    id("com.android.library") version "8.9.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
    kotlin("plugin.serialization") version "2.1.20" apply false
    id("com.google.devtools.ksp") version "2.1.20-1.0.32" apply false
    id("maven-publish")
}


tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

subprojects {
    apply(plugin = "maven-publish")
    val properties = Properties().apply {
        rootProject.file("local.properties").reader().use(::load)
    }

    val gitHubPackageKey: String = properties["gitHubPackageKey"] as String
    val gitHubPackageUser: String = properties["gitHubPackageUser"] as String
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/Zestxx/ComposeAutoUiKit")
                credentials {
                    username = gitHubPackageUser
                    password = gitHubPackageKey
                }
            }
        }
    }
//    val localMavenKey: String = properties["localMavenKey"] as String
//    val localMavenUser: String = properties["localMavenUser"] as String
//    val localMavenUrl: String = properties["localMavenUrl"] as String
//    publishing {
//        repositories {
//            maven {
//                url = uri(localMavenUrl).apply {
//                    isAllowInsecureProtocol = true
//                }
//                credentials {
//                    username = localMavenUser
//                    password = localMavenKey
//                }
//            }
//        }
//    }
}