plugins {
    java
    kotlin("multiplatform")
}

group = "cli"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(Testing.junit.params)
    testRuntimeOnly(Testing.junit.engine)
}


kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    jvm("desktop") {

    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        val commonMain by getting  {
            dependencies {
                implementation("com.github.ajalt.clikt:clikt:_")
            }
        }
        val commonTest by getting {}
        val desktopMain by getting {
            dependsOn(commonMain)
        }
        val desktopTest by getting {}
        val nativeMain by getting {
            dependsOn(commonMain)

        }
        val nativeTest by getting {
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
        rename { "git-standup" }
    }
    into(destDir)
    doLast {
        println("$ git-standup installed into $destDir")
    }
}


// TODO: copy to /usr/local/share/bash-completion/completions
tasks.register<Exec>("dq") {
    val completeCommand = "standup --generate-completion zsh > completion-standup.zsh"
    commandLine = completeCommand.split(" ")
}
