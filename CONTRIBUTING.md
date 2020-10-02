# Contributing to the Kotlin Library Playground for #hacktoberfest

## Before you start

If you haven't done so already, register to Hacktoberfest

==> <big><big>https://hacktoberfest.digitalocean.com/ </big></big>

## Pick up an issue

Head over to [the "issues" tab](https://github.com/LouisCAD/kotlin-libraries-playground/issues) 
and select a library for which you feel like contributing a usage sample

<a href="https://github.com/LouisCAD/kotlin-libraries-playground/issues"><img width="829" alt="Issues_·_LouisCAD_kotlin-libraries-playground" src="https://user-images.githubusercontent.com/459464/94722247-50594d80-0357-11eb-8ff8-edeadebd33d5.png"></a>

For the purpose of the guide, I will assume you choose to work on **Koin**

## Get the project to run locally

- 🛠 **Fork** the project
- ⬇️ Download the project locally
- ⑂ Create a git **branch** called `<LIBRARY_NAME>`
- 📁 Open the folder `kotlin-jvm` in [IntelliJ IDEA Community](https://www.jetbrains.com/idea/download) or in [Android Studio](https://developer.android.com/studio/)
- ⌛ Wait until your IDE has configured Gradle and indexed all the thing
- 📂 Open the file `kotlin-jvm/src/main/kotlin/playground/_main.kt`
- ▶️ Run the `main()` function

![](https://user-images.githubusercontent.com/459464/94725942-d3c96d80-035c-11eb-8562-5a69268d8714.png)

Or, if you are fine with using the command-line:

```bash
$ git clone https://github.com/LouisCAD/kotlin-libraries-playground
$ cd kotlin-libraries-playground
$ git checkout -b <LIBRARY_NAME>
$ cd kotlin-jvm
$ ./gradlew run
> Task :run
# square/moshi - A modern JSON library for Kotlin and Java
Test: User(name=Robert, age=42)
Test: {"name":"Robert","age":42}

# (more tests)
BUILD SUCCESSFUL in 12s
```

## Explore the other library samples

Look at the other samples for inspiration. Some notes:

1. Put your code in a different package, but for simplicity keep it in the root `playground` folder, this is why we are ignoring the warning.
2. Document what the library does and where people can find more information. People unfamiliar with the library will thank you!
3. Put your code in a `main()` function so that it can be run alone. But make sure to call it from `kotlin-jvm/src/main/kotlin/playground/_main.kt` so that it's run when all the code is run!
4. Document which library you are using, for when all samples are run
5. Try to keep all your code in this single file!
 
<img width="888" alt="kotlin-libraries-playground_–_Moshi_kt" src="https://user-images.githubusercontent.com/459464/94723482-18eba080-0359-11eb-951e-099e0d456455.png">

## Add the missing dependencies

Add all libraries your need in `build.gradle.kts`!

Note that the project is configured with [**gradle refreshVersions**](https://github.com/jmfayard/refreshVersions)

When you want to add a new library, either

1. You can quickly add new dependencies without leaving the IDEA, if the dependency is embedded in the plugin. Here you find the [list of supported libraries](https://github.com/jmfayard/refreshVersions/tree/master/plugins/dependencies/src/main/kotlin/dependencies)
2. If the dependency is not yet in `gradle refreshVersions`, use the normal Gradle syntax, but replace the version by an underscore.

```
dependencies {
    implementation(Square.okHttp3.okHttp)         // 1 
    implementation("org.kodein.di:kodein-di:_")   // 2
}
``` 

1. **Sync Gradle**. The latest version of the library you added will be added to the file `versions.properties` 

<img width="380" alt="kotlin-libraries-playground_–___IdeaProjects_kotlin-libraries-playground_CONTRIBUTING_md_and_Kodein-DI" src="https://user-images.githubusercontent.com/459464/94724653-e80c6b00-035a-11eb-9b05-30868e17c429.png">

Later, you can update dependencies version by running the task `$ ./gradlew refreshVersions`

Then you simply edit the file `versions.properties` and **Sync Gradle** again.

![](https://raw.githubusercontent.com/jmfayard/refreshVersions/master/docs/screenshots-usage/versions.properties_step02.png)

## Write your code

Here you are on your own. Well not exactly, you have the documentation of the library to follow. 

Just strive to have your code both simple and meaningful

![image](https://user-images.githubusercontent.com/459464/94724991-610bc280-035b-11eb-855d-c961b2c6717f.png)

## Avoid common mistakes

Common mistakes made in pull-requests:

- Did your put code in his own package?
- Did you call your sample from `kotlin-jvm/src/main/kotlin/playground/_main.kt`?
- Did you reformat your code before committing? Use the action `Reformat Code  Alt-Cmd-L`

## Contribute the pull-request 

- Make sure your code is run when you run the task `./gradlew runOnGitHub`. This task will be run [on GitHub Actions](https://github.com/LouisCAD/kotlin-libraries-playground/actions)
- Create your pull request. Hint: there is an action for that in IntelliJ/Android Studio

![](https://user-images.githubusercontent.com/459464/94725192-adef9900-035b-11eb-8df7-7ce9564580c4.png)


## What if I'm stuck?

Open the issue that was assigned to you, and describe why you are stuck. We will do our best to unstuck you :)

