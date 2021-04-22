package testing.failing

import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import testing.common.DistanceConverter

class FailingSpekTest : Spek({
    describe("distance parser") {

        val distanceConverter = DistanceConverter()

        it("Failing: 750 meters should parse to 0.8") {
            assertEquals(750, distanceConverter.parse(800))
        }
    }
})

