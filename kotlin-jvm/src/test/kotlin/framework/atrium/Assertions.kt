package framework.atrium

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import org.junit.Test

class Assertions {
    @Test
    fun `failing assertion group`() {
        expect(17) {
            isLessThan(4)
            isGreaterThan(20)
            isEqualComparingTo(17)
        }
    }

    @Test
    fun `single assertion`() {
        expect(8 + 10).isGreaterThan(20).isLessThan(19)
    }

    @Test
    fun `passing feature assertion`() {
        val fam = Family(listOf(FamilyMember("Fayard")))
        expect(fam)
            .feature("number of members") { members.size }.toBe(1)
    }

    @Test
    fun `passing type assertion`() {
        expect(2.0).isA<Double>()
    }

    @Test
    fun `passing collection assertion`() {
        expect(listOf("String", 12, 21.0)).contains(21)
    }

    internal data class FamilyMember(val name: String)
    internal data class Family(val members: List<FamilyMember>)

}
