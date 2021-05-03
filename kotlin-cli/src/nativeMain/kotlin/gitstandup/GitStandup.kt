import gitstandup.CliCommand
import io.stdoutOfShellCommand

fun main(args: Array<String>) {
    val command = CliCommand()
    command.main(args)

    if (command.help) {
        println(command.getFormattedHelp())
        return
    }

    val currentDir = stdoutOfShellCommand("pwd", ".", trim=true, redirectStderr = true)
    val gitRepositories = stdoutOfShellCommand(command.findCommand(),".", trim=true, redirectStderr = true)
    gitRepositories.lines().forEach { path ->
        val relativeRepositoryPath = path.removePrefix("./").removeSuffix("/.git")
        runStandup("$currentDir/$relativeRepositoryPath", command)
    }
}

fun runStandup(directoryPath: String, command: CliCommand) {

    // fetch the latest commits if necessary
    if (command.fetch) {
        val fetchCommand = "git fetch --all"
        if (command.verbose) println(fetchCommand)
        stdoutOfShellCommand(fetchCommand, directoryPath, trim=true, redirectStderr = true)
    }

    // history
    val result = stdoutOfShellCommand(command.gitLogCommand(), directoryPath, trim=true, redirectStderr = true)
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
