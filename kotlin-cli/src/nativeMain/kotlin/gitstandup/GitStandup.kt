import io.executeShellCommand

fun main() {
    val currentDir = executeShellCommand("pwd")
    val gitRepositories = executeShellCommand("find . -name '.git' -maxdepth 2")
    gitRepositories.lines().forEach { path ->
        val normalize = path.removePrefix("./").removeSuffix("/.git")
        println("$currentDir/$normalize")
        val log = executeShellCommand("cd $currentDir/$normalize && git log --oneline")
        println(log.lines().take(3))
    }
}

