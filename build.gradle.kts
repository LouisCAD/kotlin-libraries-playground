import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

group = "playground"

repositories {
    mavenLocal()
    mavenCentral()
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/")
}

dependencies {
    implementation(Kotlin.stdlib.jdk8)
    implementation("org.jetbrains.kotlin:kotlin-reflect:_")
    implementation(KotlinX.coroutines.core)

    implementation(Square.moshi)
    kapt(Square.moshi.kotlinCodegen)

    implementation(Square.retrofit2.retrofit)
    implementation(Square.retrofit2.converter.moshi)
    implementation(JakeWharton.retrofit2.converter.kotlinxSerialization)
    implementation(Square.okHttp3.okHttp)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation(Square.okHttp3.mockWebServer)

    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.properties)

    testImplementation(Testing.kotest.runner.junit5)
    testImplementation(Testing.kotest.property)
    testImplementation(Testing.kotest.assertions.core)
    testImplementation(Testing.mockK)
    testImplementation(Testing.mockK.common)
    testImplementation(Testing.mockito.core)
    testImplementation(Testing.mockito.junitJupiter)
    testImplementation(Testing.mockito.kotlin)
    testImplementation(Testing.junit)
    testImplementation(Testing.spek.dsl.jvm)
    testImplementation(Testing.spek.runner.junit5)
    testImplementation(Testing.spek.runtime.jvm)
    testImplementation(Testing.strikt.core)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("run", JavaExec::class.java) {
    this.main = "playground._mainKt"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn"
    )
}

tasks.withType(JavaExec::class.java) {
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<DefaultTask>("hello") {
    group = "Custom"
    description = "Minimal task that do nothing. Useful to debug a failing build"
}
