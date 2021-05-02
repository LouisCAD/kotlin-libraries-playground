import gitstandup.CliCommand
import io.executeShellCommand


fun main(args: Array<String>) {
    val command = CliCommand()
    command.main(args)

    val currentDir = executeShellCommand("pwd")
    val gitRepositories = executeShellCommand(command.findOptions())
    gitRepositories.lines().forEach { path ->
        val normalize = path.removePrefix("./").removeSuffix("/.git")
        runStandup("$currentDir/$normalize", command)
    }
}

fun runStandup(directoryPath: String, command: CliCommand) {
    println("# $directoryPath")

    // fetch the latest commits if necessary
    if (command.fetch) {
        executeShellCommand("git fetch --all", directoryPath)
    }

    // history
    if (command.verbose) println(command.gitLogCommand())
    val result = executeShellCommand(command.gitLogCommand(), directoryPath)
    println(result)
}
