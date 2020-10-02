package playground

fun main() {


    /**
     * Keep the list sorted to minimize merge conflicts on pull-requests!
     */
    playground.clikt.main(arrayOf("--language", "FR", "--greeting", "Bonjour"))
    playground.clikt.main(arrayOf())
    playground.di.manual.main()
    playground.exposed.main()
    playground.kodein.di.main()
    playground.kotlinpoet.main()
    playground.kotlinx.collections.immutable.main()
    playground.kotlinx.html.main()
    playground.kotlinx.serialization.main()
    playground.ktor.client.main()
    playground.moshi.main()
    playground.okhttp.main()
    playground.okio.main()
    playground.retrofit.main()
    playground.sqldelight.main()
}

infix fun <T : Any?> T.shouldBe(expected: T) {
    println("Test: $expected")
    check(this == expected) { "Test failed!\nWanted: $expected\nGot:    $this" }
}
