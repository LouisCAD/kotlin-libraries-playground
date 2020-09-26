import de.fayard.refreshVersions.bootstrapRefreshVersions

buildscript {
    val useSnapshot = false
    repositories { mavenLocal() ; gradlePluginPortal() }
    dependencies.classpath(
            if (useSnapshot) "de.fayard.refreshVersions:refreshVersions:0.9.6-SNAPSHOT" else "de.fayard.refreshVersions:refreshVersions:0.9.5"

    )
}

rootProject.name = "kotlin-libraries-playground"

bootstrapRefreshVersions()