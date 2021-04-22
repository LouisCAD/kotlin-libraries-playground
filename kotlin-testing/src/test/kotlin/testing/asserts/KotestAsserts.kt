package testing.asserts

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

class KotestAsserts {

    @Test
    fun `General assertions`() {
        "text" shouldBe "text"
        " ".shouldBeBlank()
        "hi".shouldNotBeBlank()
        "".shouldBeEmpty()
        "HI".shouldBeUpperCase()
        "hello".shouldContain("ll")

        true.shouldBeTrue()
        false.shouldBeFalse()

        null.shouldBeNull()

        10 shouldBeLessThan 11
        10 shouldBeLessThanOrEqual 10

        11 shouldBeGreaterThan 10
        11 shouldBeGreaterThanOrEqual 11

        10.shouldBeEven()
        0.shouldBeZero()
    }

    @Test
    fun `Exception assertions`() {
        shouldThrowExactly<IllegalStateException> {
            angryFunction()
        }

        shouldThrowAny {
            angryFunction()
        }
    }

    @Test
    fun `Collection assertions`() {
        emptyList<Int>().shouldBeEmpty()
        listOf(1, 2, 3) shouldContain 3
        listOf(1, 2, 3).shouldBeSorted()

        listOf(1, 2, 3) shouldBeSameSizeAs listOf(4, 5, 6)
        1 shouldBeOneOf listOf(1, 2, 3)

        emptyMap<Int, String>().shouldBeEmpty()
        mapOf(1 to "one", 2 to "two", 3 to "three") shouldContainKey 1
        mapOf(1 to "one", 2 to "two", 3 to "three") shouldContainValue "two"
        mapOf(1 to "one", 2 to "two", 3 to "three") shouldContain (3 to "three")
    }

    @Test
    fun `Arrow assertions`() {
        val optionNone = none<String>()
        val optionSome = Some("I am something").toOption()

        optionNone.shouldBeNone()
        optionSome.shouldBeSome()


        val rightEither = Either.Right(1)
        val leftEither = Either.Left("ERROR!!")

        rightEither.shouldBeRight()
        rightEither shouldBeRight 1
        leftEither.shouldBeLeft()
        leftEither shouldBeLeft "ERROR!!"

        val nonEmptyList = nonEmptyListOf(1, 2, 3, 4, 5, null)

        nonEmptyList shouldContain 1
        nonEmptyList.shouldContainNull()

        val valid = Validated.valid()
        val invalid = Validated.invalid()
        valid.shouldBeValid()
        invalid.shouldBeInvalid()

    }

    @Test
    fun `Json assertions`() {
        val jsonString = "{\"test\": \"property\", \"isTest\": true }"

        jsonString shouldMatchJson jsonString

        jsonString shouldContainJsonKey "$.test"
        jsonString shouldNotContainJsonKey "$.notTest"

        jsonString.shouldContainJsonKeyValue("$.isTest", true)
    }

    @Test
    fun `Custom assertions`() {
        val sentMail = Mail(
            dateCreated = LocalDate.of(2020, 10, 27),
            sent = true, message = "May you have an amazing day"
        )

        val unsentMail = Mail(
            dateCreated = LocalDate.of(2020, 10, 27),
            sent = false, message = "May you have an amazing day"
        )

        // This is possible
        sentMail.sent should beSent()
        unsentMail.sent shouldNot beSent()

        // This is recommended
        sentMail.shouldBeSent()
        unsentMail.shouldNotBeSent()
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
