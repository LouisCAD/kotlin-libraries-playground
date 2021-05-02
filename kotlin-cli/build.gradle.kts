plugins {
    java
    kotlin("multiplatform")
}

group = "gitstandup"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(Testing.junit.params)
    testRuntimeOnly(Testing.junit.engine)
}


kotlin {
    // TODO how to support windows and Linux?
    macosX64("native") {
        binaries {
            executable()
        }
    }
    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("com.github.ajalt.clikt:clikt:_")
                implementation ("com.github.ajalt.mordant:mordant:_")
            }
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Copy>("install") {
    val destDir = "/usr/local/bin"
    dependsOn("runDebugExecutableNative")
    from("build/bin/native/debugExecutable") {
        rename { "standup" }
    }
    into(destDir)
    doLast {
        println("$ standup installed into $destDir")
    }
}
