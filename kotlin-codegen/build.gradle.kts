plugins {
    kotlin("jvm")
    kotlin("kapt")

}

repositories {
    mavenCentral()
}

dependencies {
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
    implementation("com.google.dagger:dagger:_")
    kapt(Google.dagger.compiler)
    implementation("com.squareup.moshi:moshi:_")
    kapt(Square.moshi.kotlinCodegen)
    implementation(platform("io.arrow-kt:arrow-stack:_"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx")
    implementation( "io.arrow-kt:arrow-syntax")
    implementation( "io.arrow-kt:arrow-optics")
    kapt("io.arrow-kt:arrow-meta:_")
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
}


