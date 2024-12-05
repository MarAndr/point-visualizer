pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }

}

rootProject.name = "PointVisualizer"
include(":app")

include(":core:loading")
include(":core:network")

include(":features:points:points-api")
include(":features:points:points-impl")
include(":features:files:files-api")
include(":features:files:files-impl")

include(":ui:core")
include(":ui:navigation:navigation-api")
include(":ui:navigation:navigation-impl")
include(":ui:enterpoints")
include(":ui:graph")
