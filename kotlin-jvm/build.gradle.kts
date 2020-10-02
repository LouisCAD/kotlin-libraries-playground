import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
}

group = "playground"

repositories {
    mavenLocal()
    mavenCentral()
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/")
}

sqldelight {
    database("AppDatabase") {
        packageName = "util"
    }
    linkSqlite = false
}

// File build.gradle.kts
dependencies {

    /**
     * Keep dependencies sorted to minimize merge conflicts on pull-requests!
     */
    implementation("com.github.ajalt.clikt:clikt:_")
    implementation("com.github.ajalt.clikt:clikt:_")
    implementation("com.h2database:h2:_")
    implementation("com.h2database:h2:_")
    implementation("com.squareup.moshi:moshi:_")
    implementation("com.squareup.sqldelight:sqlite-driver:_")
    implementation("org.jetbrains.exposed:exposed-core:_")
    implementation("org.jetbrains.exposed:exposed-core:_")
    implementation("org.jetbrains.exposed:exposed-dao:_")
    implementation("org.jetbrains.exposed:exposed-dao:_")
    implementation("org.jetbrains.exposed:exposed-java-time:_")
    implementation("org.jetbrains.exposed:exposed-java-time:_")
    implementation("org.jetbrains.exposed:exposed-jdbc:_")
    implementation("org.jetbrains.exposed:exposed-jdbc:_")
    implementation("org.jetbrains.kotlin:kotlin-reflect:_")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:_")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:_")
    implementation("org.kodein.di:kodein-di:_")
    implementation(JakeWharton.retrofit2.converter.kotlinxSerialization)
    implementation(Kotlin.stdlib.jdk8)
    implementation(KotlinX.collections.immutable)
    implementation(KotlinX.collections.immutable)
    implementation(KotlinX.coroutines.core)
    implementation(KotlinX.html.jvm)
    implementation(KotlinX.html.jvm)
    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.properties)
    implementation(KotlinX.serialization.properties)
    implementation(Ktor.client.core)
    implementation(Ktor.client.okHttp)
    implementation(Ktor.client.serialization)
    implementation(Square.kotlinPoet)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation(Square.okHttp3.mockWebServer)
    implementation(Square.okHttp3.okHttp)
    implementation(Square.retrofit2.converter.moshi)
    implementation(Square.retrofit2.retrofit)
    kapt(Square.moshi.kotlinCodegen)
    testImplementation(Testing.junit)
    testImplementation(Testing.junit.params)
    testImplementation(Testing.kotest.assertions.core)
    testImplementation(Testing.kotest.property)
    testImplementation(Testing.kotest.runner.junit5)
    testImplementation(Testing.mockK)
    testImplementation(Testing.mockK.common)
    testImplementation(Testing.mockito.core)
    testImplementation(Testing.mockito.junitJupiter)
    testImplementation(Testing.mockito.kotlin)
    testImplementation(Testing.spek.dsl.jvm)
    testImplementation(Testing.spek.runner.junit5)
    testImplementation(Testing.spek.runtime.jvm)
    testImplementation(Testing.strikt.arrow)
    testImplementation(Testing.strikt.core)
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
