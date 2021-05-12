plugins {
    kotlin("jvm")
}

dependencies {
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:_")
    testImplementation("org.amshove.kluent:kluent:_")
    testImplementation(Testing.junit)
    testImplementation(Testing.junit.params)
    testImplementation(Testing.kotest.assertions.arrow)
    testImplementation(Testing.kotest.assertions.core)
    testImplementation(Testing.kotest.assertions.json)
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
    testImplementation("io.arrow-kt:arrow-core:0.11.0")
    // Keep dependencies sorted to minimize merge conflicts on pull-requests!
}

tasks.withType<Test> {
    useJUnitPlatform()
}
