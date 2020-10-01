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
// File build.gradle.kts
dependencies {
    implementation(Kotlin.stdlib.jdk8)
    implementation("org.jetbrains.kotlin:kotlin-reflect:_")
    implementation(KotlinX.coroutines.core)

    implementation("com.squareup.moshi:moshi:_")
    kapt(Square.moshi.kotlinCodegen)

    implementation(Ktor.client.core)
    implementation(Ktor.client.okHttp)
    implementation(Ktor.client.serialization)

    implementation("org.kodein.di:kodein-di:_")

    implementation(Square.retrofit2.retrofit)
    implementation(Square.retrofit2.converter.moshi)
    implementation(JakeWharton.retrofit2.converter.kotlinxSerialization)
    implementation(Square.okHttp3.okHttp)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation(Square.okHttp3.mockWebServer)

    implementation(KotlinX.serialization.core)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:_")
    implementation(KotlinX.serialization.properties)
    implementation(KotlinX.collections.immutable)

    testImplementation(Testing.junit.params)
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
    testImplementation(Testing.strikt.arrow)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("run", JavaExec::class.java) {
    this.main = "playground._mainKt"
}

/**
 * How do I setup GitHub Actions for my Gradle or Android project?
 * https://dev.to/jmfayard/how-do-i-setup-github-actions-for-my-gradle-or-android-project-3eal
 */
tasks.register("runOnGitHub") {
    dependsOn(":run")
    group = "custom"
    description = "$ ./gradlew runOnGitHub # runs on GitHub Action"
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
