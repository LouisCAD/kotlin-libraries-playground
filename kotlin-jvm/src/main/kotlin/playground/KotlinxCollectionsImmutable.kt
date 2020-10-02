@file:Suppress("PackageDirectoryMismatch")

package playground.kotlinx.collections.immutable

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf
import playground.shouldBe

/**
 * Kotlin/kotlinx.collections.immutable : Immutable Collections Library for Kotlin
 *
 * - [GitHub](https://github.com/Kotlin/kotlinx.collections.immutable)
 * - [Website](https://github.com/Kotlin/kotlinx.collections.immutable)
 * - [CHANGELOG](https://github.com/Kotlin/kotlinx.collections.immutable/blob/master/CHANGELOG.md)
 */
fun main() {
    println()
    println("# Kotlin/kotlinx.collections.immutable : Immutable Collections Library for Kotlin")

    persistentList()
    persistentSet()
    persistentMap()
}

fun persistentList() {
    val list = persistentListOf("A", "B", "C")
    list.first() shouldBe "A"

    list.filter { it == "B" } shouldBe arrayListOf("B")
    list.last() shouldBe "C"
    list.size shouldBe 3
}

fun persistentSet() {
    val set = persistentSetOf("A", "B", "C", "C")
    set.first() shouldBe "A"

    set.filter { it == "B" } shouldBe arrayListOf("B")
    set.last() shouldBe "C"
    set.size shouldBe 3
}

fun persistentMap() {
    val map = persistentMapOf(
            1 to "A",
            2 to "B",
            42 to "C"
    )

    map[1] shouldBe "A"
    map[42] shouldBe "C"
    map.values.toList() shouldBe listOf("A", "B", "C")
}
