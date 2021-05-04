package cli

import io.ExecuteCommandOptions
import io.executeCommandAndCaptureOutput
import io.fileIsReadable

val GIT = "/usr/local/bin/git"
val FIND = "/usr/bin/find"
// /usr/bin/find ~/IdeaProjects/kotlin-libraries-playground/ ~/IdeaProjects/refreshVersions  -maxdepth 2 -mindepth 0 -name .git
fun runGitStandup(args: Array<String>) {
    println("readble? " + fileIsReadable("/Users/jmfayard/.git"))
    val command = CliCommand()
    val options = ExecuteCommandOptions(directory = ".", abortOnError = true, redirectStderr = true, trim = true)
    val currentDirectory = executeCommandAndCaptureOutput(listOf("pwd"), options).trim()

    command.currentGitUser = executeCommandAndCaptureOutput(
        listOf(GIT, "config", "user.name"), options
    )
    command.main(args)

    if (command.help) {
        println(command.getFormattedHelp())
        return
    }
    val gitRepositories =
        executeCommandAndCaptureOutput(command.findCommand(), options.copy(abortOnError = false, directory = currentDirectory))
    gitRepositories.lines().filter { it.contains(".git") }.forEach { path ->
        val repositoryPath = when {
            path.startsWith("./") -> "$currentDirectory/" + path.removePrefix("./")
            else -> path
        }.removeSuffix(".git").removeSuffix("/")
        println("path: $path => $repositoryPath")
        findCommitsInRepo(repositoryPath, command)
    }
}

fun findCommitsInRepo(repositoryPath: String, command: CliCommand) {
    val options = ExecuteCommandOptions(directory = repositoryPath, abortOnError = true, redirectStderr = true, trim = true)

    if (fileIsReadable("$repositoryPath/.git").not()) {
        if (command.verbose) println("Skipping non-repository with path='$repositoryPath'")
    }
    if (command.verbose) {
        println("findCommitsInRepo($repositoryPath)")
    }
    // fetch the latest commits if necessary
    if (command.fetch) {
        val fetchCommand = listOf(GIT, "fetch", "--all")
        if (command.verbose) println(fetchCommand)
        try {
            executeCommandAndCaptureOutput(fetchCommand, options)
        } catch (e: Exception) {
            println("Warning: could not fetch commits from repository $repositoryPath ; error $e")
        }
    }

    // history
    val result = executeCommandAndCaptureOutput(
        command.gitLogCommand().also { if (command.verbose) println("$repositoryPath $ $it") },
        options)
    if (result.isNotBlank()) {
        println("# $repositoryPath")
        println(result)
    } else if (command.silence.not()) {
        println("# $repositoryPath")
        println("No commits from ${command.authorName()} during this period")
    }
    if (command.verbose) {
        println("$ " + command.gitLogCommand())
    }
}
