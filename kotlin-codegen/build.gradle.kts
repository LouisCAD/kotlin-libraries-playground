plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
    implementation("com.google.dagger:dagger:_")
    kapt(Google.dagger.compiler)
    implementation("com.squareup.moshi:moshi:_")
    kapt(Square.moshi.kotlinCodegen)
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
}


