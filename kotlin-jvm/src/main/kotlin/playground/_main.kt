package playground

import org.intellij.lang.annotations.Language
import java.io.File

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
    playground.fuel.main()
    playground.hoplite.main()
    playground.klaxon.main()
    playground.kodein.db.main()
    playground.konad.main()
    playground.konf.main()
    playground.kotlin.collections.main()
    playground.kotlinfaker.main()
    playground.kotlinio.main()
    playground.kotlinpoet.main()
    playground.kotlin_statistics.main()
    playground.kotlinx.collections.immutable.main()
    playground.kotlinx.datetime.main()
    playground.kotlinx.html.main()
    playground.kotlinx.serialization.main()
    playground.ktor.client.main()
    playground.markdown.main()
    playground.mordant.main()
    playground.moshi.main()
    playground.okhttp.main()
    playground.okio.main()
    playground.picnic.main()
    playground.retrofit.main()
    playground.skrapeit.main()
    playground.sqldelight.main()
    playground.statemachine.main()
    /**
     * Keep the list sorted to minimize merge conflicts on pull-requests!
     */
}


fun loadResourceFileContent(@Language("file-reference") path: String): String {
    return Unit.javaClass.classLoader
        .getResource(path)!!
        .readText()
}

fun loadResourceFile(@Language("file-reference") path: String): File {
    return File(
        Unit.javaClass.classLoader
            .getResource(path)!!.file
    )
}
