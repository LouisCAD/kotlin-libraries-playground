@file:Suppress("PackageDirectoryMismatch")

package playground.picnic

import com.jakewharton.picnic.table
import playground.shouldBe

/**
 * JakeWharton/picnic : A Kotlin DSL and Java/Kotlin builder API for constructing HTML-like tables which can be rendered to text.
 *
- [GitHub](https://github.com/JakeWharton/picnic)
 */
fun main() {
    println()
    println("# JakeWharton/picnic : A Kotlin DSL and Java/Kotlin builder API for constructing HTML-like tables which can be rendered to text.")

    val table = table {
        cellStyle {
            border = true
            paddingLeft = 1
            paddingRight = 1
        }
        header {
            row("Who?", "Guests?", "Brings what?")
        }
        guests.forEach { guest ->
            row(guest.who, guest.guests.joinToString(), guest.brings.joinToString())
        }
    }
    "\n$table" shouldBe """
┌─────────┬──────────────────┬─────────────────┐
│ Who?    │ Guests?          │ Brings what?    │
├─────────┼──────────────────┼─────────────────┤
│ Alice   │ Anita            │ Quiche          │
├─────────┼──────────────────┼─────────────────┤
│ Bob     │                  │ Beer            │
├─────────┼──────────────────┼─────────────────┤
│ Charlie │ Celia, Christina │ Guitar, Peanuts │
└─────────┴──────────────────┴─────────────────┘"""
}


data class PicnicGuest(
    val who: String,
    val guests: List<String>,
    val brings: List<String>
)

val guests = listOf(
    PicnicGuest(who = "Alice", guests = listOf("Anita"), brings = listOf("Quiche")),
    PicnicGuest(who = "Bob", guests = emptyList(), brings = listOf("Beer")),
    PicnicGuest(who = "Charlie", guests = listOf("Celia", "Christina"), brings = listOf("Guitar", "Peanuts")),
)

