import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
    id("com.apollographql.apollo")
}

tasks.withType<JavaExec> {
    classpath = sourceSets["main"].runtimeClasspath
}

sqldelight {
    database("AppDatabase") {
        packageName = "util"
    }
    linkSqlite = false
}

// File build.gradle.kts
dependencies {

    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
    implementation("com.beust:klaxon:_")
    implementation ("com.github.ajalt:mordant:_")
    implementation("com.github.ajalt.clikt:clikt:_")
    implementation("com.github.ajalt.clikt:clikt:_")
    implementation("com.h2database:h2:_")
    implementation("com.h2database:h2:_")
    implementation("com.squareup.moshi:moshi:_")
    implementation("com.squareup.sqldelight:sqlite-driver:_")
    implementation("io.github.serpro69:kotlin-faker:_")
    implementation("com.apollographql.apollo:apollo-runtime:_")
    implementation("com.apollographql.apollo:apollo-coroutines-support:_")
    implementation("org.jetbrains.exposed:exposed-core:_")
    implementation("org.jetbrains.exposed:exposed-core:_")
    implementation("org.jetbrains.exposed:exposed-dao:_")
    implementation("org.jetbrains.exposed:exposed-dao:_")
    implementation("org.jetbrains.exposed:exposed-java-time:_")
    implementation("org.jetbrains.exposed:exposed-java-time:_")
    implementation("org.jetbrains.exposed:exposed-jdbc:_")
    implementation("org.jetbrains.exposed:exposed-jdbc:_")
    implementation("org.jetbrains.kotlin:kotlin-reflect:_")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:_")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:_")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:_")
    implementation("org.kodein.di:kodein-di:_")
    implementation("org.koin:koin-core:_")
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
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
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
    kapt(Square.moshi.kotlinCodegen)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("run", JavaExec::class.java) {
    this.main = "playground._mainKt"
}

apollo {
    generateKotlinModels.set(true)
}