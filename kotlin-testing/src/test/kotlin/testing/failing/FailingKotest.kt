package testing.failing

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import testing.common.DistanceConverter

class FailingKotest : StringSpec({

    val converter = DistanceConverter()

    // Alternative style, using the row(arg1, arg2, ...) syntax
    "check distance parse"{
        forAll(
            row(500L, 0.5),
            row(750L, 0.8)
        ) { meter, kilometer ->
            converter.parse(meter) shouldBe kilometer
        }
    }


})

