@file:Suppress("PackageDirectoryMismatch")

package playground.clikt

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*


/**
 * ajalt/Clikt : Multiplatform command line interface parsing for Kotlin
 *
 * - [Website](https://ajalt.github.io/clikt/)
 * - [Github](https://github.com/ajalt/clikt)
 */
class CliktPlayground : CliktCommand() {
    private val language: String by option(help="Language of greeting").default("EN")
    private val greeting: String by option(help="Greeting").default("Hello")

    override fun run() {
        println()
        println("# ajalt/Clikt : Multiplatform command line interface parsing for Kotlin")
        println("Greeting: $greeting, Language: $language")
    }
}

fun main(args: Array<String>) = CliktPlayground().main(args)