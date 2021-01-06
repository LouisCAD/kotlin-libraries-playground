@file:Suppress("PackageDirectoryMismatch")

package playground.konad

import io.konad.*
import io.konad.Maybe.Companion.maybe
import io.konad.Maybe.Companion.nullable
import io.konad.applicative.builders.on
import playground.shouldBe

/**
 *  lucapiccinelli/konad
 *
 * [GitHub](https://github.com/lucapiccinelli/konad)
 */

fun main() {
    println()
    println("# lucapiccinelli/konad")
    println("Simple Kotlin monads for the everyday error handling. Brings simple nulls composition")
    println()

    println("Deal with nullables. How to compose nullables")

    var foo: Int? = 1
    var bar: String? = "2"
    var baz: Float? = 3.0f

    fun useThem(x: Int, y: String, z: Float): Int = x + y.toInt() + z.toInt()

    val result1: Int? = ::useThem.curry()
        .on(foo.maybe)
        .on(bar.maybe)
        .on(baz.maybe)
        .nullable

    result1 shouldBe 6

    println("How to provide error messages when a null is not an acceptable value")
    foo = null
    bar = null
    baz = null
    val result2: Result<Int> = ::useThem.curry()
        .on(foo.ifNull("Foo should not be null"))
        .on(bar.ifNull("Bar should not be null"))
        .on(baz.ifNull("Baz should not be null"))
        .result

    println("How to access the content of a Result")
    val expectedResultMessage = "Foo should not be null,Bar should not be null,Baz should not be null"

    println("When style")
    when (result2) {
        is Result.Ok -> result2.toString()
        is Result.Errors -> result2.description(",")
    } shouldBe expectedResultMessage

    println("Fold style")
    result2.fold(
        { it.toString() },
        { it.description(",") }) shouldBe expectedResultMessage

    println("Map style")
    result2
        .map { it.toString() }
        .ifError { it.description(",") } shouldBe expectedResultMessage


    println()
    println("How to create a Type-system with Result. See below the defitions of User, Email and PhoneNumber")
    val user: Result<User> = ::User.curry()
        .on("foo.bar")
        .on(Email.of("foo.bar")) // This email is invalid -> returns Result.Errors
        .on(PhoneNumber.of("xxx")) // This phone number is invalid -> returns Result.Errors
        .on("Foo")
        .result

    when (user) {
        is Result.Ok -> user.toString()
        is Result.Errors -> user.description(" - ")
    } shouldBe "foo.bar doesn't match an email format - xxx should match a valid phone number, but it doesn't"


    println()
    println("How to deal with collection of nullables")
    val collectionOfNullables: List<Int?> = listOf(1, 2, null, 4)
    val nullableCollection: Collection<Int>? = collectionOfNullables.flatten()

    nullableCollection shouldBe null

    println("How to deal with collection of Results")
    val collectionOfResult: List<Result<Int>> = listOf("error1".error(), 2.ok(), "error3".error(), 4.ok())
    val resultCollection: Result<Collection<Int>> = collectionOfResult.flatten()

    resultCollection.fold(
        { it.toString() },
        { it.description(",") }) shouldBe "error1,error3"
}

const val EMAIL_REGEX =
    "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)*[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
const val PHONE_NUMBER_REGEX = "^(\\+\\d{1,3})?\\s?(\\d\\s?)+\$"

data class User(val username: String, val email: Email, val phoneNumber: PhoneNumber, val firstname: String)

data class Email private constructor(val value: String) {
    companion object {
        fun of(emailValue: String): Result<Email> = if (Regex(EMAIL_REGEX).matches(emailValue))
            Email(emailValue).ok()
        else "$emailValue doesn't match an email format".error()
    }
}

data class PhoneNumber private constructor(val value: String) {
    companion object {
        fun of(phoneNumberValue: String): Result<PhoneNumber> = if (Regex(PHONE_NUMBER_REGEX).matches(phoneNumberValue))
            PhoneNumber(phoneNumberValue).ok()
        else "$phoneNumberValue should match a valid phone number, but it doesn't".error()
    }
}
