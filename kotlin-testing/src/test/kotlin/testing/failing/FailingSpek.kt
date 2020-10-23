package testing.failing

import testing.common.DistanceConverter
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import testing.common.Input

class DistanceParserParametrizedTest : Spek({
    describe("distance parser") {

        val distanceConverter = DistanceConverter()

        it("Failing: 750 meters should parse to 0.8") {
            assertEquals(750, distanceConverter.parse(800))
        }
    }
})

