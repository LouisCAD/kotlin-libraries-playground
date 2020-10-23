package testing.asserts

import org.amshove.kluent.*
import org.junit.jupiter.api.Test

/**
 * Kluent is a "Fluent Assertions" library written specifically for Kotlin.
 * It uses the Infix-Notations and Extension Functions of Kotlin to provide a fluent wrapper
 * around the JUnit-Asserts and Mockito.
 *
 * For more info, @see <a href="https://github.com/MarkusAmshove/Kluent">Kluent</a>
 */
class KluentTests {
    @Test
    fun `Basic assertions`() {
        // Kluent supports asserting with infix notations with backticks...
        "text" `should be equal to` "text"
        // and without backticks...
        "text" shouldBeEqualTo "text"
        // as well as with extension methods
        "text".shouldBeEqualTo("text")

        // Extension methods extend to numbers...
        42.shouldBePositive()
        // as well as Booleans
        true.shouldBeTrue()
        false.shouldNotBeTrue()

        // Null assertions are supported
        null.shouldBeNull()
        "text".shouldNotBeNull()

        // Assertions on collections are supported
        listOf(1, 2, 3).shouldNotBeEmpty()
        emptyList<Int>().shouldBeEmpty()
    }

    @Test
    fun `Exception assertions`() {
        // Invocations can be expected to throw any or a particular exception..
        invoking { throwingFunction() } `should throw` AnyException
        invoking { throwingFunction() } `should throw` RuntimeException::class

        // or not throw any exceptions
        invoking { notThrowingFunction() } `should not throw` AnyException
    }

    @Test
    fun `Custom assertions`() {
        val poirot = Detective(hasClue = true)
        // Custom assertions are supported
        poirot.shouldHaveAClue()
        val jacquesClouseau = Detective(hasClue = false)
        invoking { jacquesClouseau.shouldHaveAClue() } `should throw` AnyException
    }

    private fun throwingFunction() {
        throw RuntimeException()
    }

    private fun notThrowingFunction(): Boolean {
        return true
    }

    data class Detective(val hasClue: Boolean = false)

    private fun Detective.shouldHaveAClue() = this.should("The detective must have a clue") {
        this.hasClue
    }
}
