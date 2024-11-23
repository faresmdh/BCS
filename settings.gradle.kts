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
        maven ( url = "https://jitpack.io" )
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Cuberto/liquid-swipe-android")
            credentials {
                username = "faresmdh"
                password = "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"
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ( url = "https://jitpack.io" )
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Cuberto/liquid-swipe-android")
            credentials {
                username = "faresmdh"
                password = "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"
            }
        }
    }
}

rootProject.name = "Bouira Computer Science"
include(":app")
