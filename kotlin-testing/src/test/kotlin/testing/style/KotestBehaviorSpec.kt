package testing.style

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import testing.common.UseCase


class KotestBehaviorSpec : BehaviorSpec({

    Given("use case") {
        val useCase = UseCase()
        When("execute") {
            val output = useCase.execute()
            Then("return empty string") {
                output shouldBe ""
            }
        }
    }
})
