package playground

import com.github.kittinunf.fuel.core.FuelManager
import java.net.URL

fun main() {
    println()
    println("# kittinunf /fuel - The easiest HTTP networking library for Kotlin/Android")

    val url = "http://httpbin.org"
    val httpClient = FuelManager.instance

    val (_, response, _) = httpClient
        .get(url, listOf("hello" to "world"))
        .responseString()

    response.statusCode shouldBe 200
    response.url shouldBe URL("http://httpbin.org?hello=world")
}
