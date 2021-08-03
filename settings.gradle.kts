include(":android")
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

plugins {
    id("com.gradle.enterprise") version "3.6.1"
////                        # available:"3.6.2"
////                        # available:"3.6.3"
    id("de.fayard.refreshVersions") version "0.10.0"
////                            # available:"0.10.1-LOCAL-SNAPSHOT"
////                            # available:"0.10.1-SNAPSHOT"
////                            # available:"0.10.1"
////                            # available:"0.10.2-SNAPSHOT"
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
