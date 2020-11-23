package testing.failing

import org.amshove.kluent.*
import org.junit.jupiter.api.Test

/**
 * Kluent is a "Fluent Assertions" library written specifically for Kotlin.
 * It uses the Infix-Notations and Extension Functions of Kotlin to provide a fluent wrapper
 * around the JUnit-Asserts and Mockito.
 *
 * For more info, @see <a href="https://github.com/MarkusAmshove/Kluent">Kluent</a>
 */
class KluentFailing {
    @Test
    fun `Failing - Basic assertions`() {
        "Hola qué tal?" `should be equal to` "Bonjour, ca va?"
    }

    @Test
    fun `Custom assertions`() {
        val poirot = Detective(hasClue = true)
        // Custom assertions are supported
        poirot.shouldHaveAClue()
        val jacquesClouseau = Detective(hasClue = false)
        invoking { jacquesClouseau.shouldHaveAClue() } `should throw` AnyException
        // And failing a custom assertions produces the assertion's message
        jacquesClouseau.shouldHaveAClue()
    }

    data class Detective(val hasClue: Boolean = false)

    private fun Detective.shouldHaveAClue() = this.should("The detective must have a clue") {
        this.hasClue
    }
}
