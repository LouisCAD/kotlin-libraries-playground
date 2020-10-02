@file:Suppress("PackageDirectoryMismatch")

package playground.okhttp

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import playground.shouldBe

/**
 * square/okhttp: Square’s meticulous HTTP client for Java and Kotlin.
 *
 * [OkHttp website]()https://square.github.io/okhttp/)
 * [GitHub](https://github.com/square/okhttp)
 */
fun main() {
    println()
    println("# square/okhttp - A modern JSON library for Kotlin and Java")

    val client = OkHttpClient()

    val url = "http://httpbin.org/post".toHttpUrlOrNull()!!
            .newBuilder().addQueryParameter("hello", "world")
            .build()

    val formBody = FormBody.Builder()
            .add("search", "Jurassic Park")
            .build()

    val request = Request.Builder()
            .url(url)
            .header("X-My-Header", "42")
            .post(formBody)
            .build()

    val response: Response = client.newCall(request).execute()
    response.code shouldBe 200
    println(response.body!!.string())
}

