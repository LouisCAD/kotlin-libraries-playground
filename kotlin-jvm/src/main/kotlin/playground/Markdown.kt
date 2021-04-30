@file:Suppress("PackageDirectoryMismatch")

package playground.markdown

import org.intellij.markdown.IElementType
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.accept
import org.intellij.markdown.ast.acceptChildren
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.ast.visitors.Visitor
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import playground.shouldBe


/**
 * jetbrains/markdown : Multiplatform Markdown processor written in Kotlin
 *
 * - [Website](https://github.com/JetBrains/markdown)
 * - [Github](https://github.com/JetBrains/markdown)
 */
fun main(){
    generateHtml()
}


fun generateHtml() {
    val src = "Some *Markdown*"
    val flavour = CommonMarkFlavourDescriptor()
    val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
    val html = HtmlGenerator(src, parsedTree, flavour).generateHtml()
    html shouldBe """<body><p>Some <em>Markdown</em></p></body>"""
}
