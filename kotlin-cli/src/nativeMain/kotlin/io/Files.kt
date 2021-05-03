package io

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.*

/**
 * See https://nequalsonelifestyle.com/2020/11/16/kotlin-native-file-io/
 * **/


fun readAllText(filePath: String): String {
    val returnBuffer = StringBuilder()
    val file = fopen(filePath, "r") ?: throw IllegalArgumentException("Cannot open input file $filePath")

    try {
        memScoped {
            val readBufferLength = 64 * 1024
            val buffer = allocArray<ByteVar>(readBufferLength)
            var line = fgets(buffer, readBufferLength, file)?.toKString()
            while (line != null) {
                returnBuffer.append(line)
                line = fgets(buffer, readBufferLength, file)?.toKString()
            }
        }
    } finally {
        fclose(file)
    }

    return returnBuffer.toString()
}


fun writeAllText(filePath: String, text: String) {
    val file = fopen(filePath, "w") ?: throw IllegalArgumentException("Cannot open output file $filePath")
    try {
        memScoped {
            if (fputs(text, file) == EOF) throw Error("File write error")
        }
    } finally {
        fclose(file)
    }
}


fun writeAllLines(filePath: String, lines: List<String>, lineEnding: String = "\n") {
    val file = fopen(filePath, "w") ?: throw IllegalArgumentException("Cannot open output file $filePath")
    try {
        memScoped {
            lines.forEach {
                if (fputs(it + lineEnding, file) == EOF) {
                    throw Error("File write error")
                }
            }
        }
    } finally {
        fclose(file)
    }
}

fun fileIsReadable(filePath: String): Boolean =
    access(filePath, R_OK ) == 0
