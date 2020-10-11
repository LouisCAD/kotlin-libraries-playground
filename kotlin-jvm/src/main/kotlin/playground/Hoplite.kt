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

    println("Load the default yaml configuration")
    val defaultConfig = ConfigLoader()
        .loadConfigOrThrow<Config>("/hoplite/default.yaml")
    defaultConfig shouldBe Config(env = "dev", server = Server(port = 8080, redirectUrl = "/404.html"))

    println("Load the qa configuration from a property file overriding the yaml defaults")
    val qaConfig = ConfigLoader()
        .loadConfigOrThrow<Config>("/hoplite/qa.properties", "/hoplite/default.yaml")
    qaConfig shouldBe Config(env = "qa", server = Server(port = 8080, redirectUrl = "/404.html"))

    println("Load the prod yaml configuration overriding the yaml defaults")
    val prodConfig = ConfigLoader()
        .loadConfigOrThrow<Config>("/hoplite/prod.yaml", "/hoplite/default.yaml")
    prodConfig shouldBe Config(env = "prod", server = Server(port = 443, redirectUrl = "/404.html"))

    try {
        ConfigLoader().loadConfigOrThrow<Config>("/hoplite/qa.properties")
    }
    catch (e: Exception) {
        println("Display errors because the qa properties file does not contain the server values")
        println(e.message)
    }
}

data class Server(val port: Int, val redirectUrl: String)
data class Config(val env: String, val server: Server)
