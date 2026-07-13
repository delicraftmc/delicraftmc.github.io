pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.neoforged.net/releases")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        mavenCentral()
        maven("https://maven.neoforged.net/releases")
    }
}

rootProject.name = "delicraft"

include("dc-common")
include("dc-api")
include("dc-server")
include("dc-client")
