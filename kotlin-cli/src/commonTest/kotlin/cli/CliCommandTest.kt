package cli

import kotlin.test.Test
import kotlin.test.assertEquals

class CliCommandTest {

    @Test
    fun `log with no arguments`() {
        val cmd = CliCommand()
        cmd.main(emptyList())
        assertEquals(
            "git --no-pager log --all --no-merges --since=\"yesterday\" --author=\"Jean-Michel Fayard\" --abbrev-commit --oneline --pretty=format:'%Cred%h%Creset - %s %Cgreen(%cd) %C(bold blue)<%an>%Creset' --date='relative' --color=always",
            cmd.findCommand()
        )
    }

    @Test
    fun `find with no arguments`() {
        val cmd = CliCommand()
        cmd.main(emptyList())
        assertEquals("find . -maxdepth 2 -mindepth 0 -name .git", cmd.findCommand())
    }
}
