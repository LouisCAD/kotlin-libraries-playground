@file:Suppress("PackageDirectoryMismatch")

package playground.okio

import okio.ByteString
import okio.ByteString.Companion.encodeUtf8
import playground.shouldBe

/**
 * square/okio -  A modern I/O library for Android, Kotlin, and Java.

- [GitHub](https://github.com/square/okio)
- [Official Website - Documentation and API](https://square.github.io/okio/)
- [CHANGELOG](https://github.com/square/okio/blob/master/CHANGELOG.md)
 */
fun main() {
    println()
    println("# square/okio -  A modern I/O library for Android, Kotlin, and Java")
    val byteString: ByteString = "I love ☕️".encodeUtf8()
    byteString.hex() shouldBe "49206c6f766520e29895efb88f"
    byteString.sha1().hex() shouldBe "ee7a490a53637c88826856bbec9e482217b09f88"

}

