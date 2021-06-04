@file:Suppress("PackageDirectoryMismatch")

package playground.konform

import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.maximum
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.minimum
import playground.shouldBe

/**
 * Konform: Portable validations for Kotlin
 * - [GitHub](https://github.com/konform-kt/konform)
 * - [Website](https://www.konform.io/)
 */
fun main() {
    val invalidUser = UserProfile("A", -1)
    val validationResult = validateUser(invalidUser)
    validationResult[UserProfile::fullName] shouldBe listOf("must have at least 2 characters")
    validationResult[UserProfile::age] shouldBe listOf("must be at least '0'")
    validationResult.errors.size shouldBe 2
}


data class UserProfile(
    val fullName: String,
    val age: Int?
)

val validateUser = Validation<UserProfile> {
    UserProfile::fullName {
        minLength(2)
        maxLength(100)
    }

    UserProfile::age ifPresent {
        minimum(0)
        maximum(150)
    }
}
