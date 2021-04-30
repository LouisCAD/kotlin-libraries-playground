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

    // BUG: upgrading to 0.13.2 does not work https://gradle.com/s/46b4yz6jpglq4
    implementation(platform("io.arrow-kt:arrow-stack:0.11.0"))
    kapt("io.arrow-kt:arrow-meta:0.11.0")
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx")
    implementation( "io.arrow-kt:arrow-syntax")
    implementation( "io.arrow-kt:arrow-optics")
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
}


