import gitstandup.CliCommand
import io.executeShellCommand
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextStyles.*
import com.github.ajalt.mordant.terminal.Terminal

fun main(args: Array<String>) {
    val command = CliCommand()
        command.main(args)
    if (true) return
    val currentDir = executeShellCommand("pwd")
    val gitRepositories = executeShellCommand("find . -name '.git' -maxdepth 2")
    gitRepositories.lines().forEach { path ->
        val normalize = path.removePrefix("./").removeSuffix("/.git")
        println("$currentDir/$normalize")
        val log = executeShellCommand("cd $currentDir/$normalize && git log --oneline")
        println(log.lines().take(3))
    }
/**
    val termColors = TermColors()

    println(termColors.brightCyan("This is the usage of Mordant library, lets print some cool stuff on our terminal!"))
    //To print text in a particular color in supported terminals
    println(termColors.red("Hey There! , this text should ideally be in Red Color!"))

    //To print multiple colors in a single sentence
    with(termColors) {
        println("${red("Kotlin")} ${white("is")} ${blue("awesome!")}")
    }
    **/
}

