package testing.style

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import testing.common.UseCase

class KotestWordspec : WordSpec({
    "use case" should {
        val useCase = UseCase()
        "return empty string"{
            val actual = useCase.execute()
            val expected = ""

            actual shouldBe expected
        }
    }
})
