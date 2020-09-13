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
    implementation(KotlinX.coroutines.core)

    implementation(Square.moshi)
    kapt(Square.moshi.kotlinCodegen)

    implementation(Square.retrofit2.retrofit)
    implementation(Square.retrofit2.converter.moshi)
    implementation(JakeWharton.retrofit2.converter.kotlinxSerialization)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation(Square.okHttp3.mockWebServer)

    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.properties)

    testImplementation(Testing.kotest.runner.junit5)
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
