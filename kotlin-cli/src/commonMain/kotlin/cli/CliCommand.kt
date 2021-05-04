package cli

import cli.CliConfig.COMMAND_NAME
import cli.CliConfig.CURRENT_GIT_USER
import cli.CliConfig.FIND
import cli.CliConfig.GIT
import cli.CliConfig.GIT_STANDUP_WHITELIST
import com.github.ajalt.clikt.completion.completionOption
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import io.fileIsReadable
import io.readAllText

class CliCommand : CliktCommand(
    help = """
       Recall what you did on the last working day ..or be nosy and find what someone else did.
    """.trimIndent(),
    epilog = """
        Repositories will be searched in the current directory
        unless a file `.git-standup-whitelist` is found that contains repository paths.

        Examples:
            $COMMAND_NAME -a "John Doe" -w "MON-FRI" -m 3
    """.trimIndent(),
    name = COMMAND_NAME
) {
    init {
        completionOption()
    }

    val authorOpt: String by option("--author", "-a", help = "Specify author to restrict search to").default("me")
    val branch: String by option(
        "--branch",
        "-b",
        help = "Specify branch to restrict search to (unset: all branches, \"\$remote/\$branch\" to include fetches)"
    ).default("")
    val `week-day`: String by option("--week-day", "-w", help = "Specify weekday range to limit search to").default("")
    val depth: Int by option("--depth", "-m", help = "Specify the depth of recursive directory search").int()
        .default(-1)
    val `force-recursion` by option("--force-recursion", "-F", help = "Force recursion up to speficied depth")
    val `symbolic-links` by option(
        "--symbolic-links",
        "-L",
        help = "Toggle inclusion of symbolic links in recursive directory search"
    ).flag(default = false, defaultForHelp = "disabled")
    val daysTo: Int by option("-d", "--days", help = "Specify the number of days back to include").int().default(1)
    val daysUntil: Int by option("-u", "--until", help = "Specify the number of days back until this day").int()
        .default(0)
    val `date-format`: String by option(
        "-D",
        "--date-format",
        help = "Specify the number of days back until this day"
    ).default("")
    val help: Boolean by option("-h", "--help", help = "Display this help screen").flag()
    val `gpg-signed` by option(
        "-g",
        "--gpg-signed",
        help = "Show if commit is GPG signed (G) or not (N)"
    ).flag("disabled")
    val `fetch` by option("-f", "--fetch", help = "Fetch the latest commits beforehand").flag("--no-fetch")
    val `silence` by option(
        "-s",
        "--silence",
        help = "Silences the no activity message (useful when running in a directory having many repositories)"
    ).flag()
    val report by option("-r", "--report", help = "Generate a file with the report").flag()
    val `diff-stat` by option("-c", "--diff-stat", help = "Show diffstat for every matched commit")
    val afterOpt: String by option("-A", "--after", help = "List commits after this date").default("")
    val before: String by option("-B", "--before", help = "List commits before this date").default("")
    val `author-date` by option(
        "-R",
        "--author-date",
        help = "Display the author date instead of the committer date"
    ).flag()
    val verbose by option(help = "verbose").flag(defaultForHelp = "disabled")

    override fun run() {
        if (verbose) println(this)
    }

    fun gitLogCommand(): List<String> {
        val args = mutableListOf<String>()

        val branch = if (branch.isBlank()) "--all" else "--first-parent $branch"
        val since = if (daysTo == 1) "yesterday" else "$daysTo days ago"
        val after = if (afterOpt.isNotBlank()) "--after=$afterOpt" else ""
        val gitPrettyDate = if (`author-date`) "%ad" else "%cd"
        val gitDateFormat = if (`date-format`.isBlank()) "relative" else `date-format`
        val color = "always" // ???
        val author = authorName()
        var gitPrettyFormat = "'%Cred%h%Creset - %s %Cgreen($gitPrettyDate) %C(bold blue)<%an>%Creset'"
        if (`gpg-signed`) gitPrettyFormat += " %C(yellow)gpg: %G?%Creset"
        val until = when {
            daysUntil != 0 -> "--until='${daysUntil} days ago'"
            before.isNotBlank() -> "--until='$before'"
            else -> ""
        }

        args += listOf(GIT, "--no-pager", "log")
        args += branch.split(" ")
        args += "--no-merges"
        args += "--since=$since"
        args += until
        args += after
        args += "--author=$author"
        args += "--abbrev-commit"
        args += "--oneline"
        args += "--pretty=format:$gitPrettyFormat"
        args += "--date=$gitDateFormat"
        args += "--color=$color"
        if (`diff-stat` != null) args += ("--stat")
        return args
    }

    fun authorName() = when (authorOpt) {
        "all" -> ".*"
        "me" -> CURRENT_GIT_USER
        else -> authorOpt
    }


    fun findCommand(): List<String> {
        val args = mutableListOf<String>()

        args += FIND
        args += when {
            fileIsReadable(GIT_STANDUP_WHITELIST) -> readAllText(GIT_STANDUP_WHITELIST).lines().map { it.trim().removeSuffix("/") }
            else -> listOf(".")
        }
        if (`symbolic-links`) args += "-L"
        args += "-maxdepth"
        args += if (depth == -1) "2" else (depth + 1).toString()
        args += "-mindepth"
        args += "0"
        args += "-name"
        args += ".git"
        return args.also { if (verbose) println("$ $it") }
    }
}

