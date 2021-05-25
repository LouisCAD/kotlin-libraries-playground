import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("jvm").apply(false)
    id("com.github.ben-manes.versions")
}

buildscript {
    // BORDEL
    val materialDialogsVersion by extra("3.1.1")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:_")
    }
}


allprojects {
    group = "playground"

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        jcenter()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
}

/**
 * How do I setup GitHub Actions for my Gradle or Android project?
 * https://dev.to/jmfayard/how-do-i-setup-github-actions-for-my-gradle-or-android-project-3eal
 */
tasks.register("runOnGitHub") {
    dependsOn(":kotlin-jvm:run")
    group = "custom"
    description = "$ ./gradlew runOnGitHub # runs on GitHub Action"
}

tasks.register<DefaultTask>("hello") {
    group = "Custom"
    description = "Minimal task that do nothing. Useful to debug a failing build"
}


