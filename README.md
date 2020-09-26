# Kotlin Libraries Playground

A playground to gain a wider and deeper knowledge of the libraries in the Kotlin ecosystem

Also the official sample for [gradle refreshVersions](https://github.com/jmfayard/refreshVersions)

[![](https://user-images.githubusercontent.com/459464/93568735-ddcc9300-f990-11ea-952b-1c9a461f8e14.png)](http://www.youtube.com/watch?v=VhYERonB8co "Gradle refreshVersions")

### 🤔How do you keep up with all the new stuff?

There are [great resources to learn Kotlin](https://dev.to/jmfayard/best-ways-to-learn-kotlin-from-scratch-or-from-java-with-books-or-tutorials-online-or-in-the-ide-52cm). 

But once you master the language, you are not done just yet.

You now face another challenging task: become familiar with its ecosystem of libraries.

With time, you want to both acquire

- a **wider** knowledge of what good libraries are available in the ecosystem in general
- a **deeper** knowledge of some specific libraries particulary important for you

There are several inefficient ways to do that:

- reading tutorial after tutorial and being stuck in a loop where you "learn" about things you don't practice
- starting a new project from scratch for every libraries you come around - overwhelming
- trying out the library in your main project at work - a project with a compilation time of 5 minutes, who uses an older version of the library than the tutorial assume ; not sure your colleagues will be happy that you introduce a dependency you don't yet master.

### 🦅Widening your knowledge of libraries

The `kotlin-libraries-playgound` contains samples for a growing number of good Kotlin libraries including 
[Moshi, Okio, OkHttp, Retrofit, Kotlinx Serialization](https://github.com/LouisCAD/kotlin-libraries-playground/tree/master/src/main/kotlin/playground), 
[KoTest, Mockk, Spek, Strikt,](https://github.com/LouisCAD/kotlin-libraries-playground/tree/main/src/test/kotlin/framework) ...

You are very welcome to contribute new samples (see below)

For each library we have a sample usage that is

- self-contained (own package and main function, usually one file)
- simple yet meaningful (no fancy coffee machine with [termosiphon](https://github.com/google/dagger/tree/master/examples/maven/coffee/src/main/java/example/dagger))

Here is for example the sample usage for [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)

```kotlin
package playground.kotlinx.serialization

fun main() {
    println("# Kotlin/kotlinx.serialization : Kotlin multiplatform / multi-format serialization")
    val user = User(name = "Robert", age = 42)
    val json = """{"name":"Robert","age":42}"""

    Json.encodeToString(user) shouldBe json
    Json.decodeFromString<User>(json) shouldBe user
}

@Serializable
internal data class User(
    val name: String,
    val age: Int
)
```

## ❤️ Contributors welcome! #hacktoberfest

The more good libraries we cover, the better!

You are very welcome to contribute your own library sample

## 🔭 Deepening your understanding of one library

Clone this repository and make it yours.

Want to learn more about, say, [OkHttp](https://github.com/square/okhttp)?

You don't have the hassle to create a new project.

Create a new **branch** called okhttp and try out things while you are reading the [documentation](https://square.github.io/okhttp/recipes/)

## 🎩 Easy dependency management with gradle refreshVersions

This playground is also the official sample for [gradle refreshVersions](https://github.com/jmfayard/refreshVersions)

It makes it super easy to refresh dependencies

![](https://raw.githubusercontent.com/jmfayard/refreshVersions/5d646e3a0f2924b5097bf9ce680a03772807f2c2/docs/screenshots-usage/versions.properties_step02.png)

And to add a new dependency

![](https://raw.githubusercontent.com/jmfayard/refreshVersions/5d646e3a0f2924b5097bf9ce680a03772807f2c2/docs/screenshots-usage/dependencies_constants_autocomplete_2.png)