package testing.failing

import arrow.core.*
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.assertions.arrow.nel.shouldContain
import io.kotest.assertions.arrow.nel.shouldContainNull
import io.kotest.assertions.arrow.option.shouldBeNone
import io.kotest.assertions.arrow.option.shouldBeSome
import io.kotest.assertions.arrow.validation.shouldBeInvalid
import io.kotest.assertions.arrow.validation.shouldBeValid
import io.kotest.assertions.asClue
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.json.*
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.assertions.withClue
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.matchers.collections.shouldBeSorted
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.ints.shouldBeEven
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.maps.shouldContainKey
import io.kotest.matchers.maps.shouldContainValue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.string.shouldBeBlank
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldBeUpperCase
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotBeBlank
import java.time.LocalDate
import org.junit.jupiter.api.Test

/**
 * Kotest assertions
 *
 * - [Kotest Assertions Documentation](https://kotest.io/assertions/)
 * - [Github](https://github.com/kotest/kotest/)
 */

class FailingKotestAsserts {

    @Test
    fun `General assertions`() {
        assertSoftly {
            "text" shouldBe "txet"
            "hi".shouldBeBlank()
            " ".shouldNotBeBlank()
            "hi".shouldBeEmpty()
            "hi".shouldBeUpperCase()
            "hello".shouldContain("hi")

            false.shouldBeTrue()
            true.shouldBeFalse()

            "not null".shouldBeNull()

            10 shouldBeLessThan 10
            10 shouldBeLessThanOrEqual 9

            11 shouldBeGreaterThan 11
            11 shouldBeGreaterThanOrEqual 12

            9.shouldBeEven()
            1.shouldBeZero()
        }
    }

    @Test
    fun `Exception assertions`() {
        assertSoftly {
            shouldThrowExactly<IllegalArgumentException> {
                angryFunction()
            }

            shouldThrowAny {
               "I'm not throwing anything"
            }
        }
    }

    @Test
    fun `Collection assertions`() {
        assertSoftly {
            listOf(1, 2, 3).shouldBeEmpty()
            listOf(1, 2, 3) shouldContain 4
            listOf(1, 3, 2).shouldBeSorted()

            listOf(1, 2, 3, 4) shouldBeSameSizeAs listOf(4, 5, 6)
            1 shouldBeOneOf listOf(2, 3)

            mapOf(1 to "one", 2 to "two", 3 to "three").shouldBeEmpty()
            mapOf(1 to "one", 2 to "two", 3 to "three") shouldContainKey 4
            mapOf(1 to "one", 2 to "two", 3 to "three") shouldContainValue "five"
            mapOf(1 to "one", 2 to "two", 3 to "three") shouldContain (6 to "six")
        }
    }

    @Test
    fun `Arrow assertions`() {
        assertSoftly {
            val optionNone = none<String>()
            val optionSome = Some("I am something").toOption()

            optionSome.shouldBeNone()
            optionNone.shouldBeSome()

            val rightEither = Either.Right(1)
            val leftEither = Either.Left("ERROR!!")

            leftEither.shouldBeRight()
            leftEither shouldBeRight 1
            rightEither.shouldBeLeft()
            rightEither shouldBeLeft "ERROR!!"

            val nonEmptyList = nonEmptyListOf(1, 2, 3, 4, 5)

            nonEmptyList shouldContain 6
            nonEmptyList.shouldContainNull()

            val valid = Validated.valid()
            val invalid = Validated.invalid()
            invalid.shouldBeValid()
            valid.shouldBeInvalid()
        }
    }

    @Test
    fun `Json assertions`() {
        val jsonString = "{\"test\": \"property\", \"isTest\": true }"

        assertSoftly {
            jsonString shouldMatchJson "{\"test\": \"otherProperty\"}"

            jsonString shouldContainJsonKey "$.anotherTest"
            jsonString shouldNotContainJsonKey "$.test"

            jsonString.shouldContainJsonKeyValue("$.isTest", false)
        }
    }

    @Test
    fun `Custom assertions`() {
        assertSoftly {
            val sentMail = Mail(
                dateCreated = LocalDate.of(2020, 10, 27),
                sent = true, message = "May you have an amazing day"
            )

            val unsentMail = Mail(
                dateCreated = LocalDate.of(2020, 10, 27),
                sent = false, message = "May you have an amazing day"
            )

            // This is possible
            unsentMail.sent should beSent()
            sentMail.sent shouldNot beSent()

            // This is recommended
            unsentMail.shouldBeSent()
            sentMail.shouldNotBeSent()
        }
    }

    @Test
    fun `withClue usage`() {
        val mail = Mail(
            dateCreated = LocalDate.of(2020, 10, 27),
            sent = false, message = "May you have an amazing day"
        )

        withClue("sent field should be true") {
            mail.sent shouldBe true
        }

        mail.asClue {
            it.dateCreated shouldBeAfter LocalDate.of(2020, 10, 26)
            it.sent shouldBe true
        }
    }

    @Test
    fun `asClue usage`() {
        val mail = Mail(
            dateCreated = LocalDate.of(2020, 10, 27),
            sent = false, message = "May you have an amazing day"
        )

        mail.asClue {
            it.dateCreated shouldBeAfter LocalDate.of(2020, 10, 26)
            it.sent shouldBe true
        }
    }

    fun beSent() = object : Matcher<Boolean> {
        override fun test(value: Boolean) = MatcherResult(value, "Mail.sent should be true", "Mail.sent should be false")
    }

    fun Mail.shouldBeSent() = this.sent should beSent()
    fun Mail.shouldNotBeSent() = this.sent shouldNot beSent()
}

data class Mail(val dateCreated: LocalDate, val sent: Boolean, val message: String)

fun angryFunction() {
    throw IllegalStateException("How dare you!")
}
