package io

import java.io.File;

actual fun readAllText(filePath: String): String =
    File(filePath).readText()

actual fun writeAllText(filePath: String, text: String) {
    File(filePath).writeText(text)
}

actual fun writeAllLines(filePath: String, lines: List<String>) =
    File(filePath).writeText(lines.joinToString(separator = "\n"))

actual fun fileIsReadable(filePath: String): Boolean =
    File(filePath).canRead()


actual fun stdoutOfShellCommand(
    command: String,
    directory: String,
    trim: Boolean,
    redirectStderr: Boolean
): String {
    TODO("Implement stdoutOfShellCommand")
}
