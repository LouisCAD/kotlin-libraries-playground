import de.fayard.refreshVersions.bootstrapRefreshVersions

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        google()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.squareup.sqldelight") {
                useModule("com.squareup.sqldelight:gradle-plugin:1.4.3")
            }
        }
    }
}

buildscript {
    val useSnapshot = false
    repositories { mavenLocal() ; gradlePluginPortal() }
    dependencies.classpath(
            if (useSnapshot) "de.fayard.refreshVersions:refreshVersions:0.9.6-SNAPSHOT" else "de.fayard.refreshVersions:refreshVersions:0.9.5"

    )
}

plugins {
    id("com.gradle.enterprise") version "3.4.1"
}

// https://dev.to/jmfayard/the-one-gradle-trick-that-supersedes-all-the-others-5bpg
gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishOnFailure()
    }
}

rootProject.name = "kotlin-libraries-playground"

bootstrapRefreshVersions()