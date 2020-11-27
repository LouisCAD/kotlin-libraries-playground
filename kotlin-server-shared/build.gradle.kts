plugins {
    kotlin("jvm")
}

group = "playground.server"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation(Kotlin.stdlib.jdk8)
}
