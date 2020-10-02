@file:Suppress("PackageDirectoryMismatch")

package playground.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import playground.shouldBe


/**
 * square/moshi - A modern JSON library for Kotlin and Java.
 *
 * -  [square/moshi: GitHub](https://github.com/square/moshi)
 * - [JavaDoc](https://square.github.io/moshi/1.x/moshi/)
 */
fun main() {
    println()
    println("# square/moshi - A modern JSON library for Kotlin and Java")
    val moshi: Moshi = configureMoshi()
    val adapter: JsonAdapter<User> = moshi.adapter(User::class.java)

    val user = User(name = "Robert", age = 42)
    val json = """{"name":"Robert","age":42}"""

    adapter.fromJson(json) shouldBe user
    adapter.toJson(user) shouldBe json
}

fun configureMoshi(): Moshi =
    Moshi.Builder().build()

@JsonClass(generateAdapter = true)
internal data class User(
    val name: String,
    val age: Int,
)

