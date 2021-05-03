import gitstandup.CliCommand
import io.stdoutOfShellCommand

val GIT_STANDUP_WHITELIST = ".git-standup-whitelist"

fun main(args: Array<String>) {
    val command = CliCommand()
    command.main(args)

    val currentDir = stdoutOfShellCommand("pwd")
    val gitRepositories = stdoutOfShellCommand(command.findCommand())
    gitRepositories.lines().forEach { path ->
        val relativeRepositoryPath = path.removePrefix("./").removeSuffix("/.git")
        runStandup("$currentDir/$relativeRepositoryPath", command)
    }
}

fun runStandup(directoryPath: String, command: CliCommand) {
    stdoutOfShellCommand("ls -l")

    // fetch the latest commits if necessary
    if (command.fetch) {
        val fetchCommand = "git fetch --all"
        if (command.verbose) println(fetchCommand)
        stdoutOfShellCommand(fetchCommand, directoryPath)
    }

    // history
    val result = stdoutOfShellCommand(command.gitLogCommand(), directoryPath)
    if (result.isNotBlank()) {
        println("# $directoryPath")
        println(result)
    } else if (command.silence.not()){
        println("# $directoryPath")
        println("No commits from ${command.authorName()} during this period")
    }
    if (command.verbose) {
        println("$ " + command.gitLogCommand())
    }
}
