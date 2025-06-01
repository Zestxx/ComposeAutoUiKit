pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "ComposeAutoUiKit"
include(":sample")
include(":sample_uikit")
include(":auto-uikit-processor")
include(":auto-uikit")
include(":auto-uikit-core")
include(":auto-uikit-annotation")
