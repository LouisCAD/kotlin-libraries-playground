package playground.server.shared

import java.time.LocalDate
import java.util.*

object InitialData {
    val stephenKing = "Stephen King"
    val robinHobb = "Robin Hobb"
    val authors = listOf(stephenKing, robinHobb)

    val americanVampire = BookDto(
        title = "American Vampire",
        publication = LocalDate.parse("2010-11-08"),
        authorName = stephenKing
    )
    val assassinRoyal = BookDto(
        title = "L'assassin Royal",
        publication = LocalDate.parse("1998-12-17"),
        authorName = robinHobb
    )
    val books = listOf(americanVampire, assassinRoyal)
}

