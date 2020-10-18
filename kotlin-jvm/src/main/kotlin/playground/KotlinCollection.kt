@file:Suppress("PackageDirectoryMismatch")

package playground.kotlin.collections

import playground.shouldBe

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
    println("Creating and Iterating List")
    createLists()
    println("Operations on List")
    operationsOnLists()
    println("Creating and Iterating Set")
    createSets()
    println("Creating and Iterating Map")
    createMap()
    println("Operations on Map")
    operationsOnMap()
}

fun createLists() {

    val squares = List(10) { it * it }
    println("List of square contains $squares")

    var imperativeSquares = mutableListOf<Int>()
    println("Creating list using simple range .. ")
    for (i in 0..9) {
        imperativeSquares.add(i * i)
    }
    squares shouldBe imperativeSquares

    imperativeSquares = mutableListOf<Int>()
    println("Creating list iterating forward using until")
    for (i in 0 until 10) {
        imperativeSquares.add(i * i)
    }
    squares shouldBe imperativeSquares

    val squaresreverse = squares.reversed()
    println("Reversed sqaured list = $squaresreverse")
    imperativeSquares = mutableListOf<Int>()
    println("Creating list iterating backward using until")
    for (i in 9 downTo 0) {
        imperativeSquares.add(i * i)
    }
    squaresreverse shouldBe imperativeSquares

    imperativeSquares = mutableListOf<Int>()
    println("Creating list using step size -- here the step size is 3")
    for (i in 0 until 10 step 3) {
        imperativeSquares.add(i * i)
    }
    imperativeSquares shouldBe listOf(0, 9, 36, 81)

    println("Creating list using foreach")
    imperativeSquares = mutableListOf<Int>()
    squares.forEach { imperativeSquares.add(it) }
    squares shouldBe imperativeSquares
}

fun operationsOnLists() {

    val weeks = listOf<String>("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    println("Immutable list weeks = $weeks")
    println("Getting element at given index")
    weeks.get(0) shouldBe "Sunday"
    println("Getting index of an element")
    weeks.indexOf("Tuesday") shouldBe 2
    println("Checking if list contains a particular value")
    weeks.contains("Monday") shouldBe true
    weeks.contains("NotWeek") shouldBe false

    val birds = mutableListOf<String>("Crow", "Peacock", "Dove", "Sparrow", "Pigeon", "Parrot", "Flamingo")
    println("Mutable list birds = $birds")
    println("Adding element in list ")
    birds.add("Owl")
    birds shouldBe listOf<String>("Crow", "Peacock", "Dove", "Sparrow", "Pigeon", "Parrot", "Flamingo", "Owl")
    println("Adding element in list for given index")
    birds.add(1, "Penguin")
    birds shouldBe listOf<String>(
        "Crow",
        "Penguin",
        "Peacock",
        "Dove",
        "Sparrow",
        "Pigeon",
        "Parrot",
        "Flamingo",
        "Owl"
    )
    println("Removing element in list")
    birds.remove("Dove")
    birds shouldBe listOf<String>("Crow", "Penguin", "Peacock", "Sparrow", "Pigeon", "Parrot", "Flamingo", "Owl")
    println("Removing element in list at given index")
    birds.removeAt(3)
    birds shouldBe listOf<String>("Crow", "Penguin", "Peacock", "Pigeon", "Parrot", "Flamingo", "Owl")
    println("Setting element in at particular index")
    birds.set(5, "Swan")
    birds shouldBe listOf<String>("Crow", "Penguin", "Peacock", "Pigeon", "Parrot", "Swan", "Owl")
    println("Adding collection of list with list")
    val birdsToAdd = listOf<String>("Woodpecker", "Robin")
    birds.addAll(birdsToAdd)
    birds shouldBe listOf<String>(
        "Crow",
        "Penguin",
        "Peacock",
        "Pigeon",
        "Parrot",
        "Swan",
        "Owl",
        "Woodpecker",
        "Robin"
    )
    println("Adding collection of list in list")
    val birdsToRemove = listOf<String>("Penguin", "Pigeon")
    birds.removeAll(birdsToRemove)
    birds shouldBe listOf<String>("Crow", "Peacock", "Parrot", "Swan", "Owl", "Woodpecker", "Robin")
    println("Retaining certain element in list")
    val birdsToRetain = listOf<String>("Crow", "Peacock", "Swan", "Woodpecker", "Robin")
    birds.retainAll(birdsToRetain)
    birds shouldBe listOf<String>("Crow", "Peacock", "Swan", "Woodpecker", "Robin")
    println("Reversing the list")
    birds.reverse()
    birds shouldBe listOf<String>("Robin", "Woodpecker", "Swan", "Peacock", "Crow")
    println("Sorting the list")
    birds.sort()
    birds shouldBe listOf<String>("Crow", "Peacock", "Robin", "Swan", "Woodpecker")
}

fun createSets() {
    val animals = setOf<String>("Dog", "Cat", "Lion", "Dog")
    println("Set contains unique elements. Though element dog was added twice the resulting set added dog only once ")
    println("Immutable list weeks = $animals")
    animals shouldBe setOf<String>("Dog", "Cat", "Lion")

    val imperativeAnimals = mutableSetOf<String>()
    for (animal in animals) {
        imperativeAnimals.add(animal)
    }

    animals shouldBe imperativeAnimals
    println("All operations of List can be performed on Set.")
}

fun createMap() {
    println("Creating map using to operator")
    var capitals = mapOf(
        "AUSTRALIA" to "CANBERRA",
        "BELGIUM" to "BRUSSELS",
        "GERMANY" to "BERLIN",
        "INDIA" to "NEW DELHI",
        "JAPAN" to "TOKYO"
    )
    println("capitals map is $capitals")
    var imperativeCapitals = mutableMapOf<String, String>()
    println("Creating map by traversing element wise")
    for (item in capitals) {
        imperativeCapitals.put(item.key, item.value)
    }
    println(imperativeCapitals)
    capitals shouldBe imperativeCapitals

    println("Creating map using to Pair()")
    capitals = mapOf(
        Pair("AUSTRALIA", "CANBERRA"),
        Pair("BELGIUM", "BRUSSELS"),
        Pair("GERMANY", "BERLIN"),
        Pair("INDIA", "NEW DELHI"),
        Pair("JAPAN", "TOKYO")
    )
    println("capitals map is $capitals")
    imperativeCapitals = mutableMapOf<String, String>()
    println("Alternative way to create map by iterating using (key,value)")
    for ((key, value) in capitals) {
        imperativeCapitals.put(key, value)
    }
    capitals shouldBe imperativeCapitals
}

fun operationsOnMap() {
    var capitals = mapOf(
        "AUSTRALIA" to "CANBERRA",
        "BELGIUM" to "BRUSSELS",
        "GERMANY" to "BERLIN",
        "INDIA" to "NEW DELHI",
        "JAPAN" to "TOKYO"
    )
    println("Immutable Map capitals = $capitals")
    println("Checking if map contains particular key")
    capitals.containsKey("INDIA") shouldBe true
    println("Checking if map contains particular valur")
    capitals.containsValue("BRUSSELS") shouldBe true
    capitals.contains("GHANA") shouldBe false
    println("Getting value in map for given key")
    capitals.get("BELGIUM") shouldBe "BRUSSELS"

    capitals = mutableMapOf(
        "AUSTRALIA" to "CANBERRA",
        "BELGIUM" to "BRUSSELS",
        "GERMANY" to "BERLIN",
        "INDIA" to "NEW DELHI",
        "JAPAN" to "TOKYO"
    )
    println("Mutable Map capitals = $capitals")
    println("Adding key, value pair in map")
    capitals.put("FRANCE", "PARIS")
    capitals shouldBe mapOf(
        "AUSTRALIA" to "CANBERRA",
        "BELGIUM" to "BRUSSELS",
        "GERMANY" to "BERLIN",
        "INDIA" to "NEW DELHI",
        "JAPAN" to "TOKYO",
        "FRANCE" to "PARIS"
    )
    println("Removing element in map given key")
    capitals.remove("BELGIUM")
    capitals shouldBe mapOf(
        "AUSTRALIA" to "CANBERRA",
        "GERMANY" to "BERLIN",
        "INDIA" to "NEW DELHI",
        "JAPAN" to "TOKYO",
        "FRANCE" to "PARIS"
    )

    val capitalsToAdd = mapOf<String, String>("CHINA" to "BEIJING", "EGYPT" to "CAIRO")
    println("Adding Map Collection to the existing Map")
    capitals.putAll(capitalsToAdd)
    capitals shouldBe mapOf(
        "AUSTRALIA" to "CANBERRA",
        "GERMANY" to "BERLIN",
        "INDIA" to "NEW DELHI",
        "JAPAN" to "TOKYO",
        "FRANCE" to "PARIS",
        "CHINA" to "BEIJING",
        "EGYPT" to "CAIRO"
    )

}
