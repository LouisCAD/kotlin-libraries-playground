include(":android")
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

plugins {
    id("com.gradle.enterprise") version "3.6.3"
////                        # available:"3.6.4"
    id("de.fayard.refreshVersions") version "0.20.0"
}


refreshVersions {
    enableBuildSrcLibs()
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

apply(from= "gradle/detekt-android-sdk.gradle")
val hasAndroidSdk: Boolean by extra
if (hasAndroidSdk) include("android")
include("kotlin-jvm")
include("kotlin-testing")
include("kotlin-codegen")
