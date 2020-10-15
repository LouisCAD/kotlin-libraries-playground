@file:Suppress("PackageDirectoryMismatch")

package playground.kotlinio

import java.io.File
import playground.shouldBe

/**
 * Kotlin/IO - Kotlin IO API
 *
 * - [Kotlin Documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/)
 */

fun main() {
    println()
    println("# Kotlin IO - API for working with files and streams")

    val directoryPath = "references/kotlinio"

    // FileTreeWalk
    val directory = loadReferenceFile(directoryPath)

    directory.walkTopDown().map {file -> file.name }.toList() shouldBe
        listOf("kotlinio", "nested-dir", "tada.txt", "long-file.txt", ".hidden.txt", "hello.txt")

    directory.walkBottomUp().map {file -> file.name }.toList() shouldBe
        listOf("tada.txt", "nested-dir", "long-file.txt", ".hidden.txt", "hello.txt", "kotlinio")

    directory.walk(direction = FileWalkDirection.BOTTOM_UP).map {file -> file.name }.toList() shouldBe
        listOf("tada.txt", "nested-dir", "long-file.txt", ".hidden.txt", "hello.txt", "kotlinio")

    directory.walkBottomUp().maxDepth(1).map {file -> file.name }.toList() shouldBe
        listOf("nested-dir", "long-file.txt", ".hidden.txt", "hello.txt", "kotlinio")

    directory.walkBottomUp().filter { !it.isDirectory }.map {file -> file.name }.toList() shouldBe
        listOf("tada.txt", "long-file.txt", ".hidden.txt", "hello.txt")

}

private fun loadReferenceFile(path: String): File {
    return File(Unit.javaClass.classLoader
        .getResource(path)!!.file)

}
