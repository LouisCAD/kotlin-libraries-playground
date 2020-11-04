import de.fayard.refreshVersions.bootstrapRefreshVersions

buildscript {
    repositories { mavenLocal(); gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.7")
}

plugins {
    id("com.gradle.enterprise") version "3.4.1"
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
