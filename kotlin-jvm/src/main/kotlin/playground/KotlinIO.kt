@file:Suppress("PackageDirectoryMismatch")

package playground.kotlinio

import playground.loadResourceFile
import playground.shouldBe
import playground.shouldHaveSameElementsAs
import java.io.File

/**
 * Kotlin/IO - Kotlin IO API
 *
 * - [Kotlin Documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/)
 */

fun main() {
    println()
    println("# Kotlin IO - API for working with files and streams")

    // FileTreeWalk
    val directory = loadResourceFile("references/kotlinio")

    run {
        val actual = directory.walkTopDown().map { file -> file.name }.toList()
        val expected = listOf(
            "kotlinio",
            "nested-dir",
            "tada.txt",
            "long-file.txt",
            ".hidden.txt",
            "hello.txt",
            "alternate-long-file.txt"
        )
        actual shouldHaveSameElementsAs expected
    }

    run {
        val actual = directory.walkBottomUp().map { file -> file.name }.toList()
        val expected = listOf(
            ".hidden.txt",
            "alternate-long-file.txt",
            "hello.txt",
            "tada.txt",
            "nested-dir",
            "long-file.txt",
            "kotlinio"
        )
        actual shouldHaveSameElementsAs expected
    }

    run {
        val actual = directory.walk(direction = FileWalkDirection.BOTTOM_UP).map { file -> file.name }.toList()
        val expected = listOf(
            ".hidden.txt",
            "alternate-long-file.txt",
            "hello.txt",
            "tada.txt",
            "nested-dir",
            "long-file.txt",
            "kotlinio"
        )
        actual shouldHaveSameElementsAs expected
    }

    run {
        val actual = directory.walkBottomUp().maxDepth(1).map { file -> file.name }.toList()
        val expected =
            listOf(".hidden.txt", "alternate-long-file.txt", "hello.txt", "nested-dir", "long-file.txt", "kotlinio")
        actual shouldHaveSameElementsAs expected
    }

    run {
        val actual = directory.walkBottomUp().filter { !it.isDirectory }.map { file -> file.name }.toList()
        val expected = listOf(".hidden.txt", "alternate-long-file.txt", "hello.txt", "tada.txt", "long-file.txt")
        actual shouldHaveSameElementsAs expected
    }

    // File
    val helloTxt = loadResourceFile("references/kotlinio/hello.txt")

    helloTxt.extension shouldBe "txt"
    helloTxt.nameWithoutExtension shouldBe "hello"

    helloTxt.readLines() shouldBe listOf("Hello World!", "")

    helloTxt.useLines { lines ->
        val content = mutableListOf<String>()

        lines.forEach { line -> if (line.isNotBlank()) content.add(line) }
        TestFileContent(content)
    } shouldBe TestFileContent(listOf("Hello World!"))

    val newDir = createTempDir(prefix = "new", suffix = "dir", directory = directory)
    val newFile = createTempFile(prefix = "new", directory = newDir)

    newFile.writeText("Hi, I'm a new file")
    newFile.readLines() shouldBe listOf("Hi, I'm a new file")

    newFile.appendText("\nand this is a new line")
    newFile.readLines() shouldBe listOf("Hi, I'm a new file", "and this is a new line")

    newDir.deleteRecursively()
    newFile.exists() shouldBe false
    newDir.exists() shouldBe false

    val fileToCopyTo = createTempFile(prefix = "new", directory = directory)
    val longText = loadResourceFile("references/kotlinio/alternate-long-file.txt")
    longText.copyTo(fileToCopyTo, overwrite = true)
    fileToCopyTo.readLines() shouldBe longText.readLines()
    fileToCopyTo.delete()

    val newChildFile = directory.resolve("newChild.txt")
    newChildFile.name shouldBe "newChild.txt"
    newChildFile.parentFile shouldBe directory
    newChildFile.delete()

    val newSiblingFile = longText.resolveSibling(File("sibling.txt"))
    newSiblingFile.parent shouldBe longText.parent
    newSiblingFile.delete()
}

internal data class TestFileContent(val content: List<String>)
