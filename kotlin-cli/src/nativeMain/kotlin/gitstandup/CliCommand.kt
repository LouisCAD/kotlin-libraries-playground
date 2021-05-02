package gitstandup

import com.github.ajalt.clikt.completion.completionOption
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class CliCommand : CliktCommand() {
    val author: String by option(help = "author name").default("WHOAMI")
    val branch: String by option(help = "Specify branch to restrict search to (unset: all branches, \"\$remote/\$branch\" to include fetches)").default(
        ""
    )
    val `week-day`: String by option(help = "Specify weekday range to limit search to").default("")
    val depth: Int by option(help = "Specify the depth of recursive directory search").int().default(2)
    val `force-recursion` by option(help = "Force recursion up to speficied depth")
    val `symbolic-links` by option(help = "Toggle inclusion of symbolic links in recursive directory search")
    val days: Int by option(help = "Specify the number of days back to include").int().default(1)
    val `date-format`: String by option(help = "Specify the number of days back until this day").default("")
    val helpOpt by option(help = "Dispaly this help screen", names = arrayOf("-h", "--help"))
    val `gpg-signed` by option(help = "Show if commit is GPG signed (G) or not (N)", names = arrayOf("-g"))
    val `fetch` by option(help = "Fetch the latest commits beforehand")
    val `silence` by option(help = "Silences the no activity message (useful when running in a directory having many repositories)")
    val report by option(help = "").default("Generate a file with the report")
    val `diff-stat` by option(help = "Show diffstat for every matched commit")
    val after: String by option(help = "List commits after this date").default("")
    val before: String by option(help = "List commits before this date").default("")
    val `author-date` by option(help = "Display the author date instead of the committer date")

    override fun run() {
        println("author=$author help=$helpOpt days=$days")
    }

}
