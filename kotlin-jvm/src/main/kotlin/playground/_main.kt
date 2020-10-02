package playground

fun main() {
    playground.moshi.main()
    println()
    playground.kotlinx.serialization.main()
    println()
    playground.okio.main()
    println()
    playground.retrofit.main()
    println()
    playground.okhttp.main()
    println()
    playground.ktor.client.main()
    println()
    playground.kotlinx.collections.immutable.main()
    println()
    playground.kotlinx.html.main()
    println()
}

infix fun <T: Any?> T.shouldBe(expected: T) {
    println("Test: $expected")
    check(this == expected) { "Test failed!\nWanted: $expected\nGot:    $this" }
}
