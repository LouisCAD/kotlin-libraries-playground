@file:Suppress("PackageDirectoryMismatch")

package playground.mordant

import com.github.ajalt.mordant.TermColors

/**
 * ajalt/Clikt : Multiplatform command line interface parsing for Kotlin
 * - [Github](https://github.com/ajalt/mordant)
 */

fun main() {
    //Initialise TermColors
    val termColors = TermColors()

    println(termColors.brightCyan("This is the usage of Mordant library, lets print some cool stuff on our terminal!"))
    //To print text in a particular color in supported terminals
    println(termColors.red("Hey There! , this text should ideally be in Red Color!"))

    //To print multiple colors in a single sentence
    with(termColors) {
        println("${red("Kotlin")} ${white("is")} ${blue("awesome!")}")
    }

    //Foreground and background colors Note: you can initialise TermColors()  directly within 'with' or re-use pre-initialised one
    with(TermColors()) {
        println((yellow on brightGreen)("This is highlighted text on your terminal!, isn't this cool?"))
    }

    //To change the background color alone
    with(termColors) {
        println("To checkout various kotlin libraries head to ${brightBlue.bg("kotlin-playground")}")
    }

    //To create your own style and apply them anywhere!
    with(termColors) {
        val style = (bold + white + underline)
        println(style("This text is supposed to by style according to the style variable"))
        println(style("Now you have reused your styles as you can see this text allows follows the same custom styling!"))
    }

    //Nested Styles - you can combine various styles using your creativity!
    with(termColors) {
        println(white("You ${(blue on yellow)("can ${(black + strikethrough)("nest")} styles")} arbitrarily and create awesome text!"))
    }


}

