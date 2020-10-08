@file:Suppress("PackageDirectoryMismatch")

package playground.konf

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import com.uchuhimo.konf.UnsetValueException
import com.uchuhimo.konf.source.hocon


/**
 *  uchuhimo/konf
 *
 * [GitHub](https://github.com/uchuhimo/konf)
 */

fun main() {
    println()
    println("# uchuhimo/konf")
    println("Allows to read/write configuration from files, strings, resources, and more")
    println()

    println("You should specify schema of your config using subclasses of ConfigSpec")
    val config = Config {
        addSpec(CredentialsSpec)
        addSpec(ServerSpec)
    }
    println("You can find example configuration files in kotlin-jvm/src/main/resources/configuration")
    println()

    println("Config from json with default server configuration")
    val credentials = config.from.json.resource("$configPath/credentials.json")
    credentials.printConfig()

    println()
    try {
        val missingPassword = config.from.json.resource("$configPath/missing-password.json")
        println(missingPassword[CredentialsSpec.password])
        require(false) { "should throw exception" }
    } catch (e: UnsetValueException) {
        println("If required value is missing UnsetValueException exception is thrown")
        require(e.message == "item password is unset")
    }

    println()
    println("Config from HOCON (conf) with local server configuration")
    val localhost = config.from.hocon.resource("$configPath/localhost.conf")
    localhost.printConfig()

    println()
    println("Config from java properties")
    val javaProperties = config.from.properties.resource("$configPath/java.properties")
    javaProperties.printConfig()

    println()
    println("Other formats supported:")
    listOf(
        "TOML", "XML", "YAML", "JavaScript", "hierarchical map",
        "map in key-value format", "map in flat format", "system environment", "system properties"
    ).forEach {
        println("- $it")
    }
}

private fun Config.printConfig() {
    println(
        """
        Credentials:
        - username: ${this[CredentialsSpec.username]}
        - password: ${this[CredentialsSpec.password]}
        
        Server:
        - domain: ${this[ServerSpec.domain]}
        - protocol: ${this[ServerSpec.protocol]}
    """.trimIndent()
    )
}

private object CredentialsSpec : ConfigSpec() {
    val username by required<String>()
    val password by required<String>()
}

private object ServerSpec : ConfigSpec() {
    val domain by optional("example.org")
    val protocol by optional(ServerProtocol.HTTPS)
}

enum class ServerProtocol {
    HTTP, HTTPS
}

private const val configPath = "configuration"