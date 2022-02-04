#!/usr/bin/env kotlin

@file:DependsOn("it.krzeminski:github-actions-kotlin-dsl:0.6.1")

import RefreshVersions_main.Steps.userCheckout
import RefreshVersions_main.Steps.usesCommitChanges
import RefreshVersions_main.Steps.usesCreatePullRequest
import RefreshVersions_main.Steps.usesSetupJava11
import it.krzeminski.githubactions.actions.Action
import it.krzeminski.githubactions.actions.actions.CheckoutV2
import it.krzeminski.githubactions.actions.actions.SetupJavaV2
import it.krzeminski.githubactions.actions.gradle.GradleBuildActionV2
import it.krzeminski.githubactions.domain.RunnerType
import it.krzeminski.githubactions.domain.triggers.Cron
import it.krzeminski.githubactions.domain.triggers.Schedule
import it.krzeminski.githubactions.domain.triggers.WorkflowDispatch
import it.krzeminski.githubactions.dsl.JobBuilder
import it.krzeminski.githubactions.dsl.workflow
import it.krzeminski.githubactions.yaml.toYaml
import it.krzeminski.githubactions.yaml.writeToFile
import java.nio.file.Paths

val everyMondayAt7am = Cron(minute = "0", hour = "7", dayWeek = "1")

val workflow = workflow(
    name = "RefreshVersions PR",
    on = listOf(
        WorkflowDispatch(),
        Schedule(listOf(everyMondayAt7am))
    ),
    sourceFile = Paths.get("refreshVersions.main.kts"),
    targetFile = Paths.get("refreshVersions.yml"),
) {
    job("Refresh-Versions", RunnerType.UbuntuLatest) {
        userCheckout(branch = "main")
        usesSetupJava11()
        uses(
            name = "create-branch",
            action = CreateBranchV2(branch = Branches.DEPENDENCY_UPDATE),
            env = linkedMapOf("GITHUB_TOKEN" to "\${{ secrets.GITHUB_TOKEN }}")
        )
        uses("refreshVersions", GradleBuildActionV2(arguments = "refreshVersions"))
        usesCommitChanges(Branches.DEPENDENCY_UPDATE, "Refresh versions.properties")
        usesCreatePullRequest()
    }
}

println(workflow.toYaml())
workflow.writeToFile()
println("== ${workflow.targetFile} was updated ==")


object Branches {
    const val MAIN = "main"
    const val DEPENDENCY_UPDATE = "dependency-update"
}

object Steps {
    fun JobBuilder.userCheckout(branch: String) = uses(
        name = "Check out",
        action = CheckoutV2(ref = branch),
    )

    fun JobBuilder.usesSetupJava11() = uses(
        name = "setup-java",
        action = SetupJavaV2(
            distribution = SetupJavaV2.Distribution.Adopt,
            javaVersion = "11"
        )
    )

    fun JobBuilder.usesCommitChanges(branch: String, message: String) = uses(
        name = "Commit",
        AddAndCommitV8(
            authorName = "GitHub Actions",
            authorEmail = "noreply@github.com",
            newBranch = branch,
            message = message
        )
    )

    fun JobBuilder.usesCreatePullRequest() = uses(
        name = "Pull Request",
        action = PullRequestV2(
            sourceBranch = Branches.DEPENDENCY_UPDATE,
            destinationBranch = Branches.MAIN,
            prDraft = true,
            prTitle = "Upgrade gradle dependencies",
            githubToken = "\${{ secrets.GITHUB_TOKEN }}"
        )
    )
}


/**************************************************
 * Copy-pasted from https://github.com/krzema12/github-actions-kotlin-dsl/pull/62
 ***************************************************
 */


data class CreateBranchV2(
    /** The name of the branch to create. Default "release-candidate". **/
    val branch: String? = null,
    /** The SHA1 value for the branch reference. **/
    val sha: String? = null,
) : Action("peterjgrainger", "action-create-branch", "v2") {

    @Suppress("SpreadOperator")
    override fun toYamlArguments() = linkedMapOf(
        *listOfNotNull(
            branch?.let { "branch" to it },
            sha?.let { "sha" to it }
        ).toTypedArray()
    )
}

data class AddAndCommitV8(
    /** The arguments for the `git add` command */
    val add: String? = null,
    /** The name of the user that will be displayed as the author of the commit. */
    val authorName: String? = null,
    /** The email of the user that will be displayed as the author of the commit. */
    val authorEmail: String? = null,
    /** Additional arguments for the git commit command */
    val commit: String? = null,
    /** The name of the custom committer you want to use,
     * if different from the author of the commit. */
    val committerName: String? = null,
    /** The email of the custom committer you want to use,
     * if different from the author of the commit. */
    val committerEmail: String? = null,
    /** The local path to the directory where your repository is located.
     * You should use actions/checkout first to set it up. */
    val cwd: String? = null,
    /** Determines the way the action fills missing author name and email. **/
    val defaultAuthor: DefaultActor? = null,
    /** The message for the commit. */
    val message: String? = null,
    /** If this input is set, the action will push the commit to a new branch with this name. */
    val newBranch: String? = null,
    /** The way the action should handle pathspec errors from the add and remove commands.*/
    val pathspecErrorHandling: PathSpecErrorHandling? = null,
    /** Arguments for the git pull command. By default, the action does not pull. */
    val pull: String? = null,
    /** Whether to push the commit and, if any, its tags to the repo.
     * It can also be used to set the git push arguments  */
    val push: Boolean? = null,
    /** The arguments for the `git rm` command (see the paragraph below for more info) */
    val remove: String? = null,
    /** Arguments for the git tag command
     * (the tag name always needs to be the first word not preceded by an hyphen) */
    val tag: String? = null,
) : Action(
    "EndBug", "add-and-commit", "v8"
) {
    @Suppress("ComplexMethod", "SpreadOperator")
    override fun toYamlArguments() = linkedMapOf(
        *listOfNotNull(
            add?.let { "add" to it },
            authorName?.let { "author_name" to it },
            authorEmail?.let { "author_email" to it },
            commit?.let { "commit" to it },
            committerName?.let { "committer_name" to it },
            committerEmail?.let { "committer_email" to it },
            cwd?.let { "cwd" to it },
            defaultAuthor?.let { "default_author" to it.yaml },
            message?.let { "message" to it },
            newBranch?.let { "new_branch" to it },
            pathspecErrorHandling?.let { "pathspec_error_handling" to it.yaml },
            pull?.let { "pull" to it },
            push?.let { "push" to "$it" },
            remove?.let { "remove" to it },
            tag?.let { "tag" to it },
        ).toTypedArray()
    )

    sealed class DefaultActor(val yaml: String) {
        /** UserName <UserName@users.noreply.github.com> */
        object GithubActor : DefaultActor("github_actor")

        /** Your Display Name <your-actual@email.com> */
        object UserInfo : DefaultActor("user_info")

        /** github-actions <email associated with the github logo> */
        object GitHubActions : DefaultActor("github_actions")

        class Custom(yaml: String) : DefaultActor(yaml)
    }

    sealed class PathSpecErrorHandling(val yaml: String) {
        /* errors will be logged but the step won't fail */
        object Ignore : PathSpecErrorHandling("ignore")

        /** the action will stop right away, and the step will fail */
        object ExitImmediatly : PathSpecErrorHandling("exitImmediately")

        /** the action will go on, every pathspec error will be logged at the end, the step will fail. */
        object ExitAtEnd : PathSpecErrorHandling("exitAtEnd")

        class Custom(yaml: String) : PathSpecErrorHandling(yaml)
    }
}


/**
 * A GitHub Action for creating pull requests.
 * https://github.com/repo-sync/pull-request
 */
data class PullRequestV2(
    /**
     * Branch name to pull from, default is triggered branch
     */
    val sourceBranch: String? = null,
    /**
     * Branch name to sync to in this repo, default is master
     */
    val destinationBranch: String? = null,
    /**
     * Pull request title
     */
    val prTitle: String? = null,
    /**
     * Pull request body
     */
    val prBody: String? = null,
    /**
     * Pull request template
     */
    val prTemplate: String? = null,
    /**
     * Pull request reviewers
     */
    val prReviewer: List<String>? = null,
    /**
     * Pull request assignees
     */
    val prAssignee: List<String>? = null,
    /**
     * Pull request labels
     */
    val prLabel: List<String>? = null,
    /**
     * Pull request milestone
     */
    val prMilestone: String? = null,
    /**
     * Draft pull request
     */
    val prDraft: Boolean? = null,
    /**
     *  Create PR even if no changes
     */
    val prAllowEmpty: Boolean? = null,
    /**
     * GitHub token secret
     */
    val githubToken: String? = null,
) : Action("repo-sync", "pull-request", "v2") {

    @Suppress("SpreadOperator")
    override fun toYamlArguments(): LinkedHashMap<String, String> = linkedMapOf(
        *listOfNotNull(
            sourceBranch?.let { "source_branch" to it },
            destinationBranch?.let { "destination_branch" to it },
            prTitle?.let { "pr_title" to it },
            prBody?.let { "pr_body" to it },
            prTemplate?.let { "pr_template" to it },
            prReviewer?.let { "pr_reviewer" to it.joinToString(",") },
            prAssignee?.let { "pr_assignee" to it.joinToString(",") },
            prLabel?.let { "pr_label" to it.joinToString(",") },
            prMilestone?.let { "pr_milestone" to it },
            prDraft?.let { "pr_draft" to "$it" },
            prAllowEmpty?.let { "pr_allow_empty" to "$it" },
            githubToken?.let { "github_token" to it },
        ).toTypedArray()
    )
}


