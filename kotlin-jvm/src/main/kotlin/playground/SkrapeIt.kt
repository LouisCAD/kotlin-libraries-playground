@file:Suppress("PackageDirectoryMismatch")

package playground.skrapeit

import it.skrape.core.htmlDocument
import it.skrape.expect
import it.skrape.matchers.ContentTypes
import it.skrape.matchers.toBe
import it.skrape.matchers.toBePresentTimes
import it.skrape.matchers.toContain
import it.skrape.selects.and
import it.skrape.selects.html5.*
import it.skrape.skrape
import playground.loadResourceFileContent

/**
 *  skrapeit/skrape.it
 *
 * [Website](https://docs.skrape.it/docs/)
 * [GitHub](https://github.com/skrapeit/skrape.it)
 */

fun main() {
    println()
    println("# skrapeit/skrape.it")
    println(
        "A Kotlin-based HTML / XML deserialization library that places particular emphasis on ease of use and a " +
            "high level of readability by providing an intuitive DSL."
    )
    println()

    println("Pick Html Head Elements from a Doc")
    `Pick Html Head Elements from a Doc`()
    println("Pick Html Body Elements from a Doc")
    `Pick Html Body Elements from a Doc`()
    println("Pick Custom HTML tags")
    `Pick Custom HTML tags`()
    println("Build CSS selectors")
    `Build CSS selectors`()
    println("Parse and verify local HTML resource")
    `Parse and verify local HTML resource`()
    println("Parse and verify response of url")
    `Parse and verify response of url`()
}

/**
 * [Picking Html-Elements from a Doc](https://docs.skrape.it/docs/dsl/parsing-html#picking-html-elements-from-a-doc)
 */
private fun `Pick Html Head Elements from a Doc`() {
    val mockHtmlHead =
        """
    <html>
        <head>
            <link rel="shortcut icon" href="https://some.url/icon">
            <script src="https://some.url/some-script.js"></script>
            <meta name="foo" content="bar">
        </head>
    </html>
    """.trimIndent()
    htmlDocument(mockHtmlHead) {
        meta {
            withAttribute = "name" to "foo"
            findFirst {
                attribute("content") toBe "bar"
            }
        }
    }
}

/**
 * [Picking Html-Elements from a Doc](https://docs.skrape.it/docs/dsl/parsing-html#picking-html-elements-from-a-doc)
 */
private fun `Pick Html Body Elements from a Doc`() {
    val mockHtmlBody =
        """
    <html>
        <body>
            <nav>
                <ol class="navigation">
                    <li><a href="items">List Items</a></li>
                    <li><a href="items/one">List Item One</a></li>
                    <li>Item 42</li>
                </ol>
            </nav>
            i'm the body
            <h1>i'm the headline</h1>
            <main>
                <p class="foo bar">i'm a paragraph</p>
                <p>i'm a second paragraph</p>
                <p>i'm a paragraph <wbr> with word break</p>
                <p>i'm the last paragraph</p>
            </main>
        </body>
    </html>
    """.trimIndent()
    htmlDocument(mockHtmlBody) {
        h1 {
            findFirst {
                text toBe "i'm the headline"
            }
        }
        ol {
            findFirst {
                className toContain "navigation"
            }
        }
        p {
            findAll {
                toBePresentTimes(4)
                forEach {
                    it.text toContain "paragraph"
                }
            }
        }
        p {
            withClass = "foo" and "bar"
            findFirst {
                text toBe "i'm a paragraph"
            }
        }
    }
}

/**
 * [Picking Custom HTML tags](https://docs.skrape.it/docs/dsl/parsing-html#picking-custom-html-tags)
 */
private fun `Pick Custom HTML tags`() {
    val someHtml = """
    <body>
        <a-custom-tag>foo</a-custom-tag>
        <a-custom-tag class="some-style">bar</a-custom-tag>
    </body>
    """.trimIndent()

    htmlDocument(someHtml) {
        "a-custom-tag" {
            withClass = "some-style"
            findFirst {
                text toBe "bar"
            }
        }
    }
}

/**
 * [Building CSS selectors](https://docs.skrape.it/docs/dsl/parsing-html#building-css-selectors)
 */
private fun `Build CSS selectors`() {

    val complexHtmlElement = """<button class="foo bar" fizz="buzz" disabled>click me</button>"""

    // terse
    htmlDocument(complexHtmlElement) {
        "button.foo.bar[fizz='buzz'][disabled]" {
            findFirst { /* will pick first matching occurrence */ }
            findAll { /* will pick all matching occurrences */ }
        }
    }

    // more readable and less error prone
    htmlDocument(complexHtmlElement) {
        button {
            withClass = "foo" and "bar"
            withAttribute = "fizz" to "buzz"
            withAttributeKey = "disabled"
            findFirst { /* will pick first matching occurence */ }
            findAll { /* will pick all matching occurences */ }
        }
    }
}

/**
 * [Testing an Endpoint that is returning HTML](https://docs.skrape.it/docs/dsl/basic-test-scenario#testing-an-endpoint-that-is-returning-html)
 */
private fun `Parse and verify response of url`() {
    skrape {
        url = "https://github.com/skrapeit/skrape.it"
        expect {
            statusCode toBe 200
            //statusMessage toBe "OK"
            contentType toBe ContentTypes.TEXT_HTML_UTF8

            htmlDocument {
                p {
                    findFirst {
                        text toBe "A Kotlin-based testing/scraping/parsing library providing the ability to analyze" +
                            " and extract data from HTML (server & client-side rendered). It places particular emphasis" +
                            " on ease of use and a high level of readability by providing an intuitive DSL. It aims" +
                            " to be a testing lib, but can also be used to scrape websites in a convenient fashion."
                    }
                }
            }
        }
    }
}

private fun `Parse and verify local HTML resource`() {
    htmlDocument(loadResourceFileContent("references/kotlinx.html/ref.html")) {
        h2 {
            findFirst {
                text toBe "kotlinx.html"
            }
        }
        p {
            withClass = "text-center" and "mt-6"
            findFirst {
                text toBe "If you don't want to receive letters like this - unsubscribe here."
            }
        }
    }
}

