package gitstandup

import com.github.ajalt.clikt.completion.completionOption
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class CliCommand : CliktCommand(
    help = """
       Recall what you did on the last working day ..or be nosy and find what someone else did.
    """.trimIndent(),
    epilog = """
        Examples:
            standup -a "John Doe" -w "MON-FRI" -m 3
    """.trimIndent(),
    name = "standup"
) {
    init {
        completionOption()
    }
    val author: String by option("--author", "-a", help = "author name").default("WHOAMI")
    val branch: String by option("--branch", "-b", help = "Specify branch to restrict search to (unset: all branches, \"\$remote/\$branch\" to include fetches)").default("")
    val `week-day`: String by option("--week-day", "-w", help = "Specify weekday range to limit search to").default("")
    val depth: Int by option("--depth", "-m", help = "Specify the depth of recursive directory search").int().default(2)
    val `force-recursion` by option("--force-recursion", "-F", help = "Force recursion up to speficied depth")
    val `symbolic-links` by option("--symbolic-links", "-L", help = "Toggle inclusion of symbolic links in recursive directory search").flag(default = false, defaultForHelp = "disabled")
    val days: Int by option("-d", "--days", help = "Specify the number of days back to include").int().default(1)
    val `date-format`: String by option("-D", "--date-format", help = "Specify the number of days back until this day").default("")
    val help: Boolean by option("-h", "--help", help = "Dispaly this help screen").flag()
    val `gpg-signed` by option("-g", "--gpg-signed", help = "Show if commit is GPG signed (G) or not (N)").flag("disabled")
    val `fetch` by option("-f", "--fetch", help = "Fetch the latest commits beforehand")
    val `silence` by option("-s", "--silence", help = "Silences the no activity message (useful when running in a directory having many repositories)").flag()
    val report by option("-r", "--report", help = "Generate a file with the report").flag()
    val `diff-stat` by option("-c", "--diff-stat", help = "Show diffstat for every matched commit")
    val after: String by option("-A", "--after", help = "List commits after this date").default("")
    val before: String by option("-B", "--before", help = "List commits before this date").default("")
    val `author-date` by option("-R", "--author-date", help = "Display the author date instead of the committer date").flag()

    override fun run() {
        println(this)
        if (help) println(getFormattedHelp())
    }

}
