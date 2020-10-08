package framework.kotest

import framework.common.UseCase
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class UseCaseTest : WordSpec({
    "use case" should {
        val useCase = UseCase()
        "return empty string"{
            val actual = useCase.execute()
            val expected = ""

            actual shouldBe expected
        }
    }
})
