plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
    implementation(Google.dagger)
    kapt(Google.dagger.compiler)
    implementation(Square.moshi)
    kapt(Square.moshi.kotlinCodegen)
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
}


