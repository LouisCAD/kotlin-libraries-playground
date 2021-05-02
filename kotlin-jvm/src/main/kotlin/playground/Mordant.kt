@file:Suppress("PackageDirectoryMismatch")

package playground.mordant

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextStyles.*
import com.github.ajalt.mordant.terminal.Terminal

/**
 * ajalt/Clikt : Multiplatform command line interface parsing for Kotlin
 * - [Github](https://github.com/ajalt/mordant)
 */

fun main() {
    //Initialise TermColors
    val terminal = Terminal()

    terminal.println(brightCyan("This is the usage of Mordant library, lets print some cool stuff on our terminal!"))
    //To print text in a particular color in supported terminals
    terminal.println(red("Hey There! , this text should ideally be in Red Color!"))

    //To print multiple colors in a single sentence
    terminal.println("${red("Kotlin")} ${white("is")} ${blue("awesome!")}")


    //Foreground and background colors Note: you can initialise TermColors()  directly within 'with' or re-use pre-initialised one
    terminal.println((yellow on brightGreen)("This is highlighted text on your terminal!, isn't this cool?"))

    //To change the background color alone
    terminal.println("To checkout various kotlin libraries head to ${brightBlue.bg("kotlin-playground")}")

    //To create your own style and apply them anywhere!
    run {
        val style = (bold + white + underline)
        terminal.println(style("This text is supposed to by style according to the style variable"))
        terminal.println(style("Now you have reused your styles as you can see this text allows follows the same custom styling!"))
    }

    //Nested Styles - you can combine various styles using your creativity!
    terminal.println(white("You ${(blue on yellow)("can ${(black + strikethrough)("nest")} styles")} arbitrarily and create awesome text!"))


}

