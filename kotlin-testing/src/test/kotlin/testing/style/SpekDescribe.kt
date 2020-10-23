package testing.style

import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import testing.common.UseCase


object SpekDescribe : Spek({
    describe("use case") {
        val useCase = UseCase()
        it("should return empty string") {
            val actual = useCase.execute()
            val expected = ""
            assertEquals(expected, actual)
        }
    }
})
