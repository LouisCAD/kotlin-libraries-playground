package testing.style

import testing.common.DistanceConverter
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import testing.common.Input

class SpekParametrizedTest : Spek({
    describe("distance parser") {

        val distanceConverter = DistanceConverter()

        listOf(Input(500, 0.5), Input(750, 0.750))
                .forEach { input ->
                    it("meters ${input.parameter} should parse to ${input.expected}") {
                        assertEquals(input.expected, distanceConverter.parse(input.parameter))
                    }
                }

    }
})

