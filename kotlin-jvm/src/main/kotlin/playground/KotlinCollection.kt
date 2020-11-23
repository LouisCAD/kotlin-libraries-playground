@file:Suppress("PackageDirectoryMismatch")

package playground.kotlin.collections

import playground.shouldBe
import playground.test

/**
 * kotlin-stdlib/kotlin.collections - Collection types, such as Iterable, Collection, List, Set, Map and related top-level and extension functions.
 *
 * - [ kotlin-stdlib/kotlin.collections : Github] (https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/collections/Collections.kt)
 * - [ Documentation] (https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/)
 */

fun main() {
    println()
    println("# kotlin/collections - Collections library for kotlin")
    println()
    println("Main collections in Kotlin are list, set and map")
    println("Creating Lists")
    createList()
    println("Operations on List")
    operationsOnLists()
    println("Creating and Iterating Set")
    createSets()
    println("Creating and Iterating Map")
    createMap()
    println("Operations on Map")
    operationsOnMap()
    println("Operations on Sequences")
    fibonacciSequences()
}


fun createList() {
    val imperativeSquares = mutableListOf<Int>()
    for (i in 0..9) {
        imperativeSquares.add(i * i)
    }

    val squares = List(10) { it * it }

    squares shouldBe imperativeSquares
}

fun operationsOnLists() {

    val weeks = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    println("Immutable list weeks = $weeks")

    weeks[0] test "get by index" shouldBe "Sunday"
    weeks.indexOf("Tuesday") test "get index" shouldBe 2

    println("Checking if list contains a particular value")
    ("Monday" in weeks) test "in list" shouldBe true
    ("NotWeek" in weeks) test "not in list" shouldBe false

    val birds = listOf("Crow", "Peacock", "Swan", "Woodpecker", "Robin")
    println("Birds = $birds")
    birds.size test "size" shouldBe 5
    birds.reversed() test "reversed" shouldBe listOf("Robin", "Woodpecker", "Swan", "Peacock", "Crow")
    birds.sorted() test "sorted" shouldBe listOf("Crow", "Peacock", "Robin", "Swan", "Woodpecker")
    birds.take(3) test "take" shouldBe listOf("Crow", "Peacock", "Swan")
    birds.takeLast(3) test "takeLast" shouldBe listOf("Swan", "Woodpecker", "Robin")
    birds.filter { it.length == 4 } test "filter" shouldBe listOf("Crow", "Swan")
}

fun createSets() {
    // Set contains unique elements. Though element dog was added twice the resulting set added dog only once
    val animals = setOf<String>("Dog", "Cat", "Lion", "Dog")
    animals shouldBe setOf<String>("Dog", "Cat", "Lion")
    // This is a very fast operation with a set
    animals.contains("Dog") test "in" shouldBe true
}

data class Country(val name: String, val capital: String)

private val countries = listOf(
    Country("AUSTRALIA", "CANBERRA"),
    Country("BELGIUM", "BRUSSELS"),
    Country("GERMANY", "BERLIN"),
    Country("INDIA", "NEW DELHI"),
    Country("JAPAN", "TOKYO"),
)

fun createMap() {
    val imperativeCapitals = mutableMapOf<String, String>()
    for (country in countries) {
        imperativeCapitals[country.name] = country.capital
    }
    val capitals = countries.associate { c -> c.name to c.capital }
    capitals shouldBe imperativeCapitals

    capitals shouldBe mapOf(
        "AUSTRALIA" to "CANBERRA",
        "BELGIUM" to "BRUSSELS",
        "GERMANY" to "BERLIN",
        "INDIA" to "NEW DELHI",
        "JAPAN" to "TOKYO",
    )
}

fun operationsOnMap() {
    val capitals = countries.associate { c -> c.name to c.capital }
    capitals.containsKey("INDIA") test "containsKey" shouldBe true
    capitals.containsValue("BRUSSELS") test "containsValue" shouldBe true
    capitals["BELGIUM"] test "get" shouldBe "BRUSSELS"
    capitals.contains("GHANA") test "contains" shouldBe false

    val capitalsToAdd = mapOf("CHINA" to "BEIJING", "EGYPT" to "CAIRO")
    println("Adding Map Collection to the existing Map")
    (capitals + capitalsToAdd) shouldBe mapOf(
        "AUSTRALIA" to "CANBERRA",
        "BELGIUM" to "BRUSSELS",
        "GERMANY" to "BERLIN",
        "INDIA" to "NEW DELHI",
        "JAPAN" to "TOKYO",
        "CHINA" to "BEIJING",
        "EGYPT" to "CAIRO",
    )
}


fun fibonacciSequences() {
    fibonacci().take(10).toList() shouldBe listOf(1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
}

fun fibonacci() = sequence<Int> {
    yield(1)
    yield(1)
    var current = 1
    var previous = 1
    while (true) {
        val next = current + previous
        previous = current
        current = next
        yield(current)
    }
}
