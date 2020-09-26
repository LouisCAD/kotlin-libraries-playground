package framework.kotest

import framework.common.DistanceConverter
import framework.common.Kilometer
import framework.common.Meter
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe


class DistanceConverterParameterizedTest : StringSpec({

  val converter = DistanceConverter()

  "Convert meter to kilometer" {
    val inputs = listOf(
      Input(500L, 0.5),
      Input(750L, 0.8)
    )

    fun Meter.toKilometer(): Kilometer =
      converter.parse(this)

    inputs.forAll { (meter, kilometer) ->
      meter.toKilometer() shouldBe kilometer
    }
  }


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


data class Input(val parameter: Meter, val expected: Kilometer)


