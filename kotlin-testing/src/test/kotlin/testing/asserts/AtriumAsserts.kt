package testing.asserts

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test

class AtriumAsserts {

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
        expect(listOf("String", 12, 21.0)).contains(21.0)
    }

    @Test
    fun `passing data driven assertion`() {
        fun myFun(i: Int) = (i + 97).toChar()

        expect("calling myFun with...") {
            mapOf(
                1 to 'b',
                2 to 'c',
                3 to 'd'
            ).forEach { (arg, result) ->
                feature { f(::myFun, arg) }.toBe(result)
            }
        }
    }

    internal data class FamilyMember(val name: String)
    internal data class Family(val members: List<FamilyMember>)

}
