import de.fayard.refreshVersions.bootstrapRefreshVersions
import de.fayard.refreshVersions.migrateRefreshVersionsIfNeeded

buildscript {
    repositories { mavenLocal(); gradlePluginPortal() }
    val useSnapshot = false
    dependencies.classpath(
        if (useSnapshot) "de.fayard.refreshVersions:refreshVersions:0.9.6-SNAPSHOT" else "de.fayard.refreshVersions:refreshVersions:0.9.5"
////                                                                                                                    # available:0.9.6"
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

migrateRefreshVersionsIfNeeded("0.9.5") // Will be automatically removed by refreshVersions when upgraded to the latest version.

bootstrapRefreshVersions()

include("kotlin-jvm")
include("kotlin-testing")
include("kotlin-codegen")