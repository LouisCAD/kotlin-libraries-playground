import de.fayard.refreshVersions.bootstrapRefreshVersions

pluginManagement {
    val (stableVersion, devVersion, githubPR) = listOf("0.9.8", null, null)
    fun module(name: String) = when {
        githubPR != null -> "com.github.jmfayard:$name:PR${githubPR}-SNAPSHOT"
        devVersion != null -> "de.fayard.refreshVersions:$name:$devVersion"
        else -> "de.fayard.refreshVersions:$name:$stableVersion"
    }

    repositories {
        gradlePluginPortal()
        if (devVersion != null) mavenLocal()
        if (githubPR != null) maven("https://jitpack.io")
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "de.fayard.refreshVersions" -> useModule(module("refreshVersions"))
                "de.fayard.buildSrcLibs" -> useModule(module(
                    if (githubPR != null) "refreshVersions" else "buildSrcLibs"
                ))
            }
        }
    }
}

plugins {
    id("com.gradle.enterprise") version "3.4.1"
    id("de.fayard.buildSrcLibs")
    id("de.fayard.refreshVersions")
}

// https://dev.to/jmfayard/the-one-gradle-trick-that-supersedes-all-the-others-5bpg
gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
        buildScanPublished {
            file("buildscan.log").appendText("${java.util.Date()} - $buildScanUri\n")
        }
    }

}

rootProject.name = "kotlin-libraries-playground"

bootstrapRefreshVersions()

include("kotlin-jvm")
include("kotlin-testing")
include("kotlin-codegen")
