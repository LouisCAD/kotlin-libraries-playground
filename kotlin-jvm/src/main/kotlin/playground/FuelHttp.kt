@file:Suppress("PackageDirectoryMismatch")

package playground.fuel

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.interceptors.LogRequestInterceptor
import com.github.kittinunf.fuel.core.interceptors.LogResponseInterceptor
import com.github.kittinunf.fuel.serialization.responseObject
import playground.kotlinx.serialization.HttpBinGet
import playground.shouldBe

/**
 * Fuel HTTP Client

- [Website](https://fuel.gitbook.io/documentation/)
- [GitHub kittinunf/fuel]( https://github.com/kittinunf/fuel)
 */

fun main() {
    println()
    println("# kittinunf /fuel - The easiest HTTP networking library for Kotlin/Android")

    val url = "http://httpbin.org/get"
    val httpClient: FuelManager = FuelManager()
        .addRequestInterceptor(LogRequestInterceptor)
        .addResponseInterceptor(LogResponseInterceptor)

    val request: Request = httpClient.get(url, listOf("hello" to "world"))
    request.responseObject<HttpBinGet> { _, response, (httpBin: HttpBinGet?, error) ->
        response.statusCode shouldBe 200
        error shouldBe null
        println("httpbin=" + requireNotNull(httpBin))
        httpBin.url shouldBe "http://httpbin.org?hello=world"
    }.join()
}
