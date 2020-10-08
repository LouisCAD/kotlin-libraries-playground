plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
    id("com.apollographql.apollo")
}

sqldelight {
    database("AppDatabase") {
        packageName = "util"
    }
    linkSqlite = false
}

apollo {
    generateKotlinModels.set(true)
}

dependencies {
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
    implementation("com.apollographql.apollo:apollo-coroutines-support:_")
    implementation("com.apollographql.apollo:apollo-runtime:_")
    implementation("com.squareup.moshi:moshi:_")
    implementation("com.squareup.sqldelight:sqlite-driver:_")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:_")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:_")
    kapt(Square.moshi.kotlinCodegen)
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
}


