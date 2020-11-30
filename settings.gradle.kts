import de.fayard.refreshVersions.bootstrapRefreshVersions

pluginManagement {
    val version = "0.9.8-dev-002" // O.9.7 0.9.8:PR 0.9.8-SNAPSHOT
    repositories {
        gradlePluginPortal()
        when {
            version.contains(":PR") -> maven("https://jitpack.io")
            version.contains("-dev-") -> maven("https://dl.bintray.com/jmfayard/maven")
            version.contains("-SNAPSHOT") -> mavenLocal()
        }
    }
    resolutionStrategy {
        fun module(module: String) = when {
            version.contains(":PR") -> "com.github.jmfayard:refreshVersions:$version"
            else -> "de.fayard.refreshVersions:$module:$version"
        }
        eachPlugin {
            when (requested.id.id) {
                "de.fayard.refreshVersions" -> useModule(module("refreshVersions"))
                "de.fayard.buildSrcLibs" -> useModule(module("buildSrcLibs"))
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
