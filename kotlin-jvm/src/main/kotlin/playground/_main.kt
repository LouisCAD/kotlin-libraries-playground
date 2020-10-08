package playground

fun main() {
    /**
     * Keep the list sorted to minimize merge conflicts on pull-requests!
     */
    playground.apollo.main()
    playground.clikt.main(arrayOf("--language", "FR", "--greeting", "Bonjour"))
    playground.clikt.main(arrayOf())
    playground.di.kodein.main()
    playground.di.koin.main()
    playground.di.manual.main()
    playground.exposed.main()
    playground.klaxon.main()
    playground.konf.main()
    playground.kotlinfaker.main()
    playground.kotlinpoet.main()
    playground.kotlinx.collections.immutable.main()
    playground.kotlinx.datetime.main()
    playground.kotlinx.html.main()
    playground.kotlinx.serialization.main()
    playground.ktor.client.main()
    playground.mordant.main()
    playground.moshi.main()
    playground.okhttp.main()
    playground.okio.main()
    playground.retrofit.main()
    playground.sqldelight.main()
    /**
     * Keep the list sorted to minimize merge conflicts on pull-requests!
     */
}

infix fun <T : Any?> T.shouldBe(expected: T) {
    println("Test: $expected")
    check(this == expected) { "Test failed!\nWanted: $expected\nGot:    $this" }
}
