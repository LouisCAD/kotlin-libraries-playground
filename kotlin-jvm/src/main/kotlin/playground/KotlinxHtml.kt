@file:Suppress("PackageDirectoryMismatch")

package playground.kotlinx.html

import io.ktor.http.LinkHeader
import kotlinx.html.FlowContent
import kotlinx.html.HtmlTagMarker
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.code
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.img
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.pre
import kotlinx.html.stream.appendHTML
import kotlinx.html.title
import kotlinx.html.unsafe
import playground.shouldBe
import java.io.File


/*
  kotlinx.html

  [Github](https://github.com/kotlin/kotlinx.html/)
 */


/* Enable this to render result to file and open it in a browser. */
private const val PLAYGROUND_MODE = false

fun main() {
    println()
    println("# kotlinx.html")

    val libraryInfo = loadLibraryInfo()

    val generatedHtml = StringBuilder()
        .appendHTML()
        .html {
            head {
                meta(charset = "UTF-8")
                title { +"Kotlin libraries daily | kotlinx.html" }

                link(
                    href = "https://unpkg.com/tailwindcss@1.8.10/dist/tailwind.min.css",
                    rel = LinkHeader.Rel.Stylesheet,
                )
            }
            body(Classes.body) {
                centeredOnPage {
                    renderLibraryInformation(libraryInfo)
                }
            }
        }.toString()

    when (PLAYGROUND_MODE) {
        true -> {
            val file = File("build", "kotlinx_html.html")
                .also { it.parentFile.mkdirs() }

            file.writeText(generatedHtml)
            println(">> file://${file.absolutePath} <<")
        }
        false -> {
            generatedHtml shouldBe loadReferenceResult()
        }
    }
}

private fun loadLibraryInfo() = Library(
    name = "kotlinx.html",
    description = "A kotlinx.html library provides DSL to build HTML to Writer/Appendable or DOM at JVM and browser (or other JavaScript engine) for better Kotlin programming for Web.",
    githubUrl = "https://github.com/Kotlin/kotlinx.html",
    documentationUrl = "https://github.com/kotlin/kotlinx.html/wiki/Getting-started",
    codeSampleHtml = """
            <span class="${Classes.codeKeyword}">html</span> {
              <span class="${Classes.codeKeyword}">body</span> {
                <span class="${Classes.codeKeyword}">div</span>(<span class="${Classes.codeString}">"content"</span>) { +<span class="${Classes.codeString}">"Hello, world"</span> }
              }
            }
        """.trimIndent(),
)

@HtmlTagMarker
private fun FlowContent.centeredOnPage(block: FlowContent.() -> Unit) {
    div(Classes.rootContainer) {
        div(Classes.contentContainer) {
            block()
        }
    }
}

private fun FlowContent.renderLibraryInformation(info: Library) {
    renderLogo()
    renderTitle(info.name)
    renderDescription(info.description)
    renderLinks(
        github = info.githubUrl,
        documentation = info.documentationUrl,
    )
    renderCodeSample(info.codeSampleHtml)
    renderFooter()
}

private fun FlowContent.renderLogo() {
    div(Classes.topImage) {
        img {
            src = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjEuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkxheWVyXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4IgoJIHZpZXdCb3g9IjAgMCA2MCA2MCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNjAgNjA7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4KPGc+CgoJCTxsaW5lYXJHcmFkaWVudCBpZD0iWE1MSURfM18iIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIiB4MT0iMTUuOTU5NCIgeTE9Ii0xMy4wMTQzIiB4Mj0iNDQuMzA2OCIgeTI9IjE1LjMzMzIiIGdyYWRpZW50VHJhbnNmb3JtPSJtYXRyaXgoMSAwIDAgLTEgMCA2MSkiPgoJCTxzdG9wICBvZmZzZXQ9IjkuNjc3MDAwZS0wMiIgc3R5bGU9InN0b3AtY29sb3I6IzAwOTVENSIvPgoJCTxzdG9wICBvZmZzZXQ9IjAuMzAwNyIgc3R5bGU9InN0b3AtY29sb3I6IzIzOEFEOSIvPgoJCTxzdG9wICBvZmZzZXQ9IjAuNjIxMSIgc3R5bGU9InN0b3AtY29sb3I6IzU1N0JERSIvPgoJCTxzdG9wICBvZmZzZXQ9IjAuODY0MyIgc3R5bGU9InN0b3AtY29sb3I6Izc0NzJFMiIvPgoJCTxzdG9wICBvZmZzZXQ9IjEiIHN0eWxlPSJzdG9wLWNvbG9yOiM4MDZFRTMiLz4KCTwvbGluZWFyR3JhZGllbnQ+Cgk8cG9seWdvbiBpZD0iWE1MSURfMl8iIHN0eWxlPSJmaWxsOnVybCgjWE1MSURfM18pOyIgcG9pbnRzPSIwLDYwIDMwLjEsMjkuOSA2MCw2MCAJIi8+CgoJCTxsaW5lYXJHcmFkaWVudCBpZD0iU1ZHSURfMV8iIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIiB4MT0iNC4yMDkyIiB5MT0iNDguOTQwOSIgeDI9IjIwLjY3MzQiIHkyPSI2NS40MDUiIGdyYWRpZW50VHJhbnNmb3JtPSJtYXRyaXgoMSAwIDAgLTEgMCA2MSkiPgoJCTxzdG9wICBvZmZzZXQ9IjAuMTE4MyIgc3R5bGU9InN0b3AtY29sb3I6IzAwOTVENSIvPgoJCTxzdG9wICBvZmZzZXQ9IjAuNDE3OCIgc3R5bGU9InN0b3AtY29sb3I6IzNDODNEQyIvPgoJCTxzdG9wICBvZmZzZXQ9IjAuNjk2MiIgc3R5bGU9InN0b3AtY29sb3I6IzZENzRFMSIvPgoJCTxzdG9wICBvZmZzZXQ9IjAuODMzMyIgc3R5bGU9InN0b3AtY29sb3I6IzgwNkVFMyIvPgoJPC9saW5lYXJHcmFkaWVudD4KCTxwb2x5Z29uIHN0eWxlPSJmaWxsOnVybCgjU1ZHSURfMV8pOyIgcG9pbnRzPSIwLDAgMzAuMSwwIDAsMzIuNSAJIi8+CgoJCTxsaW5lYXJHcmFkaWVudCBpZD0iU1ZHSURfMl8iIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIiB4MT0iLTEwLjEwMTciIHkxPSI1LjgzNjIiIHgyPSI0NS43MzE1IiB5Mj0iNjEuNjY5NCIgZ3JhZGllbnRUcmFuc2Zvcm09Im1hdHJpeCgxIDAgMCAtMSAwIDYxKSI+CgkJPHN0b3AgIG9mZnNldD0iMC4xMDc1IiBzdHlsZT0ic3RvcC1jb2xvcjojQzc1N0JDIi8+CgkJPHN0b3AgIG9mZnNldD0iMC4yMTM4IiBzdHlsZT0ic3RvcC1jb2xvcjojRDA2MDlBIi8+CgkJPHN0b3AgIG9mZnNldD0iMC40MjU0IiBzdHlsZT0ic3RvcC1jb2xvcjojRTE3MjVDIi8+CgkJPHN0b3AgIG9mZnNldD0iMC42MDQ4IiBzdHlsZT0ic3RvcC1jb2xvcjojRUU3RTJGIi8+CgkJPHN0b3AgIG9mZnNldD0iMC43NDMiIHN0eWxlPSJzdG9wLWNvbG9yOiNGNTg2MTMiLz4KCQk8c3RvcCAgb2Zmc2V0PSIwLjgyMzIiIHN0eWxlPSJzdG9wLWNvbG9yOiNGODg5MDkiLz4KCTwvbGluZWFyR3JhZGllbnQ+Cgk8cG9seWdvbiBzdHlsZT0iZmlsbDp1cmwoI1NWR0lEXzJfKTsiIHBvaW50cz0iMzAuMSwwIDAsMzEuNyAwLDYwIDMwLjEsMjkuOSA2MCwwIAkiLz4KPC9nPgo8L3N2Zz4K"
            alt = "Kotlin logo"
            width = "100"
            height = "100"
        }
    }
}

private fun FlowContent.renderTitle(title: String) {
    h2(Classes.title) { +title }
}

private fun FlowContent.renderDescription(description: String) {
    p(Classes.description) { +description }
}

private fun FlowContent.renderLinks(github: String, documentation: String) {
    fun FlowContent.renderLink(icon: String, name: String, url: String) {
        p {
            img(classes = Classes.linkIcon) {
                src = icon
            }
            a(classes = Classes.linkReference) {
                href = url
                +name
            }
        }

    }

    div(Classes.linksBlock) {
        renderLink(
            icon = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij48cGF0aCBkPSJNMTIgMGMtNi42MjYgMC0xMiA1LjM3My0xMiAxMiAwIDUuMzAyIDMuNDM4IDkuOCA4LjIwNyAxMS4zODcuNTk5LjExMS43OTMtLjI2MS43OTMtLjU3N3YtMi4yMzRjLTMuMzM4LjcyNi00LjAzMy0xLjQxNi00LjAzMy0xLjQxNi0uNTQ2LTEuMzg3LTEuMzMzLTEuNzU2LTEuMzMzLTEuNzU2LTEuMDg5LS43NDUuMDgzLS43MjkuMDgzLS43MjkgMS4yMDUuMDg0IDEuODM5IDEuMjM3IDEuODM5IDEuMjM3IDEuMDcgMS44MzQgMi44MDcgMS4zMDQgMy40OTIuOTk3LjEwNy0uNzc1LjQxOC0xLjMwNS43NjItMS42MDQtMi42NjUtLjMwNS01LjQ2Ny0xLjMzNC01LjQ2Ny01LjkzMSAwLTEuMzExLjQ2OS0yLjM4MSAxLjIzNi0zLjIyMS0uMTI0LS4zMDMtLjUzNS0xLjUyNC4xMTctMy4xNzYgMCAwIDEuMDA4LS4zMjIgMy4zMDEgMS4yMy45NTctLjI2NiAxLjk4My0uMzk5IDMuMDAzLS40MDQgMS4wMi4wMDUgMi4wNDcuMTM4IDMuMDA2LjQwNCAyLjI5MS0xLjU1MiAzLjI5Ny0xLjIzIDMuMjk3LTEuMjMuNjUzIDEuNjUzLjI0MiAyLjg3NC4xMTggMy4xNzYuNzcuODQgMS4yMzUgMS45MTEgMS4yMzUgMy4yMjEgMCA0LjYwOS0yLjgwNyA1LjYyNC01LjQ3OSA1LjkyMS40My4zNzIuODIzIDEuMTAyLjgyMyAyLjIyMnYzLjI5M2MwIC4zMTkuMTkyLjY5NC44MDEuNTc2IDQuNzY1LTEuNTg5IDguMTk5LTYuMDg2IDguMTk5LTExLjM4NiAwLTYuNjI3LTUuMzczLTEyLTEyLTEyeiIvPjwvc3ZnPg==",
            name = "Github",
            url = github,
        )
        renderLink(
            icon = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij48cGF0aCBkPSJNNiAyMnYtMTZoMTZ2Ny41NDNjMCA0LjEwNy02IDIuNDU3LTYgMi40NTdzMS41MTggNi0yLjYzOCA2aC03LjM2MnptMTgtNy42MTR2LTEwLjM4NmgtMjB2MjBoMTAuMTg5YzMuMTYzIDAgOS44MTEtNy4yMjMgOS44MTEtOS42MTR6bS0xMCAxLjYxNGgtNXYtMWg1djF6bTUtNGgtMTB2MWgxMHYtMXptMC0zaC0xMHYxaDEwdi0xem0yLTdoLTE5djE5aC0ydi0yMWgyMXYyeiIvPjwvc3ZnPg==",
            name = "Documentation",
            url = documentation,
        )
    }
}

private fun FlowContent.renderCodeSample(sampleRawHtml: String?) {
    if (sampleRawHtml.isNullOrBlank()) return

    div {
        code {
            pre(Classes.codeBlock) {
                unsafe { +sampleRawHtml }
            }
        }
    }
}

private fun FlowContent.renderFooter() {
    p(Classes.unsubscribeBlock) {
        +"If you don't want to receive letters like this - "
        a(classes = Classes.unsubscribeLink) {
            href = "#"
            +"unsubscribe"
        }
        +" here."
    }
}

private object Classes {

    const val body = "bg-gray-100"
    const val rootContainer = "grid min-h-screen"
    const val contentContainer = "m-auto max-w-xl bg-blue-100 border p-8 rounded"

    const val topImage = "flex justify-center mb-6"
    const val title = "text-xl font-bold text-center mb-4"
    const val description = "text-justify"
    const val linksBlock = "py-4"
    const val linkIcon = "inline h-4 align-baseline"
    const val linkReference = "underline hover:text-red-500 ml-1"

    const val unsubscribeBlock = "text-sm text-center mt-6"
    const val unsubscribeLink = "underline hover:text-red-500"

    const val codeBlock = "p-4 bg-black text-gray-400"
    const val codeKeyword = "font-bold"
    const val codeString = "text-yellow-400"
}

private data class Library(
    val name: String,
    val description: String,
    val githubUrl: String,
    val documentationUrl: String,
    val codeSampleHtml: String? = null,
)

private fun loadReferenceResult(): String {
    return Classes.javaClass
        .classLoader
        .getResource("references/kotlinx.html/ref.html")!!
        .readText()
}
