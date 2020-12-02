@file:Suppress("PackageDirectoryMismatch")

package playground.fuel

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.serialization.responseObject
import playground.kotlinx.serialization.HttpBinGet
import playground.shouldBe
import java.net.URL

/**
 * Fuel HTTP Client

- [Website](https://fuel.gitbook.io/documentation/)
- [GitHub kittinunf/fuel]( https://github.com/kittinunf/fuel)
 */

fun main() {
    println()
    println("# kittinunf /fuel - The easiest HTTP networking library for Kotlin/Android")

    val url = "http://httpbin.org"
    val httpClient = FuelManager.instance

    httpClient
        .get(url, listOf("hello" to "world"))
        .responseObject<HttpBinGet> { _, response, result ->
            val httpBin = result.component1()!!

            response.statusCode shouldBe 200
            httpBin.url shouldBe "http://httpbin.org?hello=world"
            httpBin.args shouldBe mapOf("hello" to "world")
        }
}
