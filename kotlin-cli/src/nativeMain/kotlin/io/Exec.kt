package io

import kotlinx.cinterop.*
import platform.posix.*

/**
 * https://stackoverflow.com/questions/57123836/kotlin-native-execute-command-and-get-the-output
 */
actual fun stdoutOfShellCommand(
    command: String, // "find . -name .git"
    directory: String,
    trim: Boolean,
    redirectStderr: Boolean
): String {
     chdir(directory)

    val commandToExecute = if (redirectStderr) "$command 2>&1" else command
    val fp = popen(commandToExecute, "r") ?: error("Failed to run command: $command")

    val stdout = buildString {
        val buffer = ByteArray(4096)
        while (true) {
            val input = fgets(buffer.refTo(0), buffer.size, fp) ?: break
            append(input.toKString())
        }
    }

    val status = pclose(fp)
    if (status != 0) {
        throw Exception("Command `$command` failed with status $status${if (redirectStderr) ": $stdout" else ""}")
    }

    return if (trim) stdout.trim() else stdout
}
