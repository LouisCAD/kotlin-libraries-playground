@file:Suppress("PackageDirectoryMismatch")

package playground.kotlinfaker

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.create
import io.github.serpro69.kfaker.provider.Address
import java.util.*

/**
 * serpro69/kotlin-faker - Port of a popular ruby faker gem written completely in kotlin. Generates realistically looking fake data such as names, addresses, banking details, and many more, that can be used for testing purposes during development and testing.
 *
 * - [Github](https://github.com/serpro69/kotlin-faker)
 */

fun main() {
    println()
    println("# Kotlin-faker - Port of a popular ruby faker gem written in Kotlin.")

    val faker = Faker()
    println("Name: ${faker.name.nameWithMiddle()} ${faker.name.lastName()}")
    println("Address: ${faker.address.streetAddress()} ${faker.address.secondaryAddress()} ${faker.address.stateAbbr()} ${faker.address.country()}")
    println("Phone number: ${faker.phoneNumber.phoneNumber()}")
    val coffeeCountry = faker.coffee.country()
    println("Coffe - Blend: ${faker.coffee.blendName()} From: ${faker.coffee.regions(coffeeCountry)}, $coffeeCountry - Notes: ${faker.coffee.notes()}")
    println("Rick & Morty - Character: ${faker.rickAndMorty.characters()} Location: ${faker.rickAndMorty.locations()} Quote: ${faker.rickAndMorty.quotes()}")

    println("Kotlin-faker with locale ES")

    val fakerConfig = FakerConfig.builder().create {
        locale = "es"
        random = Random(42)
        uniqueGeneratorRetryLimit = 90
    }

    val fakerWithConfig = Faker(fakerConfig)
    fakerWithConfig.unique.enable(faker::address)
    val excludedCountries = listOf(
            "Albania",
            "Argelia",
            "Andorra",
            "Angola",
            "Argentina"
    )

    fakerWithConfig.unique.exclude<Address>("country", excludedCountries)

    println("Name: ${fakerWithConfig.name.nameWithMiddle()} ${fakerWithConfig.name.lastName()}")
    println("Adress: ${fakerWithConfig.address.streetAddress()} ${fakerWithConfig.address.secondaryAddress()} ${fakerWithConfig.address.stateAbbr()} ${fakerWithConfig.address.country()}")
    println("Phone number: ${fakerWithConfig.phoneNumber.phoneNumber()}")
}