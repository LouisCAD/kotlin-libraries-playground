package testing.style

import io.kotest.core.spec.style.FreeSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import testing.common.DistanceConverter
import testing.common.Input

class KotestFreeSpec : FreeSpec({
    val converter = DistanceConverter()
    val inputs = listOf(
        Input(500L, 0.5),
        Input(750L, 0.750)
    )

    // "String - Lambda" declares a group of tests, like a subclass with Junit5
    "Convert meter to kilometer" - {
        for ((parameter, expected) in inputs) {
            // Here we declare a test with a name that is resolved at runtime!
            "$parameter -> $expected" {
                converter.parse(parameter) shouldBe expected
            }
        }
    }

    fun square(a: Int) = a*a

    "squares" {
       val testData = listOf(
           2 to 4,
           3 to 9,
           4 to 16,
           5 to 25
       )
        testData.forAll { (input, expected) ->
            square(input) shouldBe expected
        }
    }

})
