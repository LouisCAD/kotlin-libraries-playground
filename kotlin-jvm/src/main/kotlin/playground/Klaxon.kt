@file:Suppress("PackageDirectoryMismatch")

package playground.klaxon

import com.beust.klaxon.Klaxon
import playground.shouldBe


/**
 * klaxon - A library to parse JSON in Kotlin.
 *
 * -  [klaxon: GitHub](https://github.com/cbeust/klaxon)
 */
fun main() {
    println("# klaxon - A library to parse JSON in Kotlin.")
    val json = """{"id" : 1, "name" : "Adam"}"""
    val user = User(id = 1, name = "Adam")
    val klaxon = Klaxon()

    klaxon.parse<User>(json) shouldBe user
    klaxon.toJsonString(user) shouldBe json
}

internal data class User(val id: Int, val name: String)