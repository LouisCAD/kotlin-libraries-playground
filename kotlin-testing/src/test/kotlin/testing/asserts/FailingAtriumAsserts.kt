package testing.asserts


import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test

class FailingAtriumAsserts {
    @Test
    fun `failing assertion group`() {
        expect(17) {
            isLessThan(4)
            isGreaterThan(20)
            isEqualComparingTo(17)
        }
    }

    @Test
    fun `number is greater than 20 and less than 19`() {
        expect(8 + 10).isGreaterThan(20).isLessThan(19)
    }

    @Test
    fun `list should contain values greater than 10`() {
        expect(listOf(2, 5, 6, 7, 8,)).all { isGreaterThan(10) }
    }

    @Test
    fun `all keys should start with b`() {
        expect(mapOf("benny" to 1, "curry" to 2, "derry" to 3)){
            keys { all { startsWith("b") } }
        }
    }
}
