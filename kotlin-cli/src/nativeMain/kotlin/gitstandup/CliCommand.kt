package gitstandup

import com.github.ajalt.clikt.completion.completionOption
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import io.executeShellCommand

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
    val authorOpt: String by option("--author", "-a", help = "author name").default("me")
    val branch: String by option("--branch", "-b", help = "Specify branch to restrict search to (unset: all branches, \"\$remote/\$branch\" to include fetches)").default("")
    val `week-day`: String by option("--week-day", "-w", help = "Specify weekday range to limit search to").default("")
    val depth: Int by option("--depth", "-m", help = "Specify the depth of recursive directory search").int().default(-1)
    val `force-recursion` by option("--force-recursion", "-F", help = "Force recursion up to speficied depth")
    val `symbolic-links` by option("--symbolic-links", "-L", help = "Toggle inclusion of symbolic links in recursive directory search").flag(default = false, defaultForHelp = "disabled")
    val daysTo: Int by option("-d", "--days", help = "Specify the number of days back to include").int().default(1)
    val daysUntil : Int by option("-u", "--until", help = "Specify the number of days back until this day").int().default(0)
    val `date-format`: String by option("-D", "--date-format", help = "Specify the number of days back until this day").default("")
    val help: Boolean by option("-h", "--help", help = "Dispaly this help screen").flag()
    val `gpg-signed` by option("-g", "--gpg-signed", help = "Show if commit is GPG signed (G) or not (N)").flag("disabled")
    val `fetch` by option("-f", "--fetch", help = "Fetch the latest commits beforehand").flag("--no-fetch")
    val `silence` by option("-s", "--silence", help = "Silences the no activity message (useful when running in a directory having many repositories)").flag()
    val report by option("-r", "--report", help = "Generate a file with the report").flag()
    val `diff-stat` by option("-c", "--diff-stat", help = "Show diffstat for every matched commit")
    val after: String by option("-A", "--after", help = "List commits after this date").default("")
    val before: String by option("-B", "--before", help = "List commits before this date").default("")
    val `author-date` by option("-R", "--author-date", help = "Display the author date instead of the committer date").flag()
    val verbose by option(help = "verbose").flag(defaultForHelp = "disabled")
    override fun run() {
        println(this)
        if (help) println(getFormattedHelp())
    }

    fun gitLogCommand() = buildString {
        val branch = if (branch.isBlank()) "--all" else "--first-parent $branch"
        val since = if (daysTo == 1) "yesterday" else "$daysTo days ago"
        val until = when {
            daysUntil != 0 -> "--until='${daysUntil} days ago'"
            before.isNotBlank() -> "--until='$before'"
            else -> ""
        }
        val gitPrettyDate = if (`author-date`) "%ad" else "%cd"
        val gitPrettyFormat="%Cred%h%Creset - %s %Cgreen($gitPrettyDate) %C(bold blue)<%an>%Creset"
        val gitDateFormat= if (`date-format`.isBlank()) "relative" else `date-format`
        val color = "always" // ???
        val author = when(authorOpt) {
            "all" -> ".*"
            "me"-> executeShellCommand("git config user.name")
            else -> authorOpt
        }
        append("git '--no-pager' 'log' '$branch' '--no-merges' ")
        append(" --since='$since' $until ")
        append(" --author='$author' ")
        append(" --abbrev-commit --oneline ")
        append(" --pretty=format:'$gitPrettyFormat' --date='$gitDateFormat' ")
        append(" --color='$color' ")
        if (`diff-stat` != null) append(" --stat ")
    }

    fun findOptions() = buildString {
        append("find . ")
        val maxDepth = if (depth == -1) 2 else (depth + 1)
        append(" -maxdepth $maxDepth ")

        if (`symbolic-links`) append(" -L ")

        append(" -mindepth 0 -name .git ")
    }.also { println("$ $it") }

}
