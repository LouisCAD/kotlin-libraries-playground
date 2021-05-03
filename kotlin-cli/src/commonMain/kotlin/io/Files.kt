package io

expect fun readAllText(filePath: String): String

expect fun writeAllText(filePath: String, text: String)

expect fun writeAllLines(
    filePath: String,
    lines: List<String>
)

expect fun fileIsReadable(filePath: String): Boolean

expect fun stdoutOfShellCommand(
    command: String,
    directory: String,
    trim: Boolean,
    redirectStderr: Boolean
): String
