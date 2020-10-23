@file:Suppress("PackageDirectoryMismatch")

package playground.hoplite

import com.sksamuel.hoplite.ConfigLoader
import playground.shouldBe

/**
 * Hoplite : Kotlin library for loading configuration files into typesafe classes in a boilerplate-free way
 *
 * - [GitHub](https://github.com/sksamuel/hoplite)
 */
fun main() {
    println()
    println("# sksamuel/hoplite")
    println("You can find example configuration files in kotlin-jvm/src/main/resources/hoplite/")

    println("Load the default Hocon configuration")
    val defaultConfig = ConfigLoader()
        .loadConfigOrThrow<Config>("/hoplite/default.conf")
    defaultConfig shouldBe Config(env = "dev", server = Server(port = 8080, redirectUrl = "/v1/404.html"))

    println("Load the qa configuration from a property file cascading over the Hocon defaults")
    val qaConfig = ConfigLoader()
        .loadConfigOrThrow<Config>("/hoplite/qa.properties", "/hoplite/default.conf")
    qaConfig shouldBe Config(env = "qa", server = Server(port = 8080, redirectUrl = "/v1/404.html"))

    println("Load the prod Yaml configuration cascading over the Hocon defaults")
    val prodConfig = ConfigLoader()
        .loadConfigOrThrow<Config>("/hoplite/prod.yaml", "/hoplite/default.conf")
    prodConfig shouldBe Config(env = "prod", server = Server(port = 443, redirectUrl = "/v1/404.html"))

    try {
        ConfigLoader().loadConfigOrThrow<Config>("/hoplite/qa.properties")
    } catch (e: Exception) {
        println("Display errors because the qa properties file does not contain the server values")
        println(e.message)
    }
}

data class Server(val port: Int, val redirectUrl: String)
data class Config(val env: String, val server: Server)
