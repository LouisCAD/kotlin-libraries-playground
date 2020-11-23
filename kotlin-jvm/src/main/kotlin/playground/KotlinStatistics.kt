@file:Suppress("PackageDirectoryMismatch")

package playground.kotlin_statistics

import org.nield.kotlinstatistics.*
import org.nield.kotlinstatistics.range.Range
import playground.kotlin_statistics.Language.*
import playground.shouldBe


/**
 * thomasnield/kotlin-statistics : Idiomatic statistical operators for Kotlin
 * - [Github](https://github.com/thomasnield/kotlin-statistics)
 */

fun main() {
    println("")
    println("# thomasnield/kotlin-statistics : Idiomatic statistical operators for Kotlin")

    basicOperators()
    slicingOperators()
    naiveBayes()
}

private fun basicOperators() {
    // Common "average" measures
    numericData.average() shouldBe 15.9
    numericData.median() shouldBe 4.0
    numericData.mode().toList() shouldBe listOf(1)

    // Distribution parameters
    numericData.geometricMean() shouldBe 4.047941076906332
    numericData.standardDeviation() shouldBe 38.43161314450499

    // Normalization
    listOf(2, 8, 14).normalize()
        .toList() shouldBe listOf(-1.0, 0.0, 1.0)
}

private fun slicingOperators() {
    // Count entries by characteristics
    repoData.countBy { it.stars > 10_000 } shouldBe mapOf(true to 3, false to 5)

    // Calculate median by name length
    repoData.medianBy(
        keySelector = { it.name.length },
        valueSelector = { it.stars }
    ) shouldBe mapOf(
        6 to 38684.5,
        4 to 13038.0,
        15 to 3660.0,
        8 to 2472.0,
        17 to 744.0,
        5 to 571.0,
    )

    // Calculate total stars by language
    repoData.map { it.language to it.stars }
        .sumBy()
        .shouldBe(
            mapOf(
                Kotlin to 46110,
                Java to 62310,
                Scala to 2472,
            )
        )

    // Split all entries into bins by stars count
    repoData.binByInt(10_000, { it.stars }, 0).bins
        .map { bin -> bin.range to bin.value.names() }
        .prepareForComparison()
        .shouldBe(
            listOf(
                0..9_999 to listOf("compose-samples", "korge", "ktor", "scalatra", "kotlin-statistics"),
                10_000..19_999 to listOf("gson"),
                20_000..29_999 to emptyList(),
                30_000..39_999 to listOf("kotlin"),
                40_000..49_999 to listOf("RxJava"),
            )
        )
}

// Automatically group incoming emails with Naive Bayes Classifier
private fun naiveBayes() {
    val emails = listOf(
        Email("Your last chance to prepare for holidays with us! Order now and get a discount!", Mailbox.Advertisement),
        Email("Autumn fall dropping prices! Hurry to make your order!", Mailbox.Advertisement),
        Email(
            "Today we’re releasing Kotlin 1.4.0 with several new language features, including the long-awaited SAM conversions for Kotlin interfaces.",
            Mailbox.Updates
        ),
        Email(
            "Hello, User! The password for your account has successfully been changed. If you did not initiate this change, please contact your administrator immediately.",
            Mailbox.Transactional
        ),
        Email(
            "Somebody requested password change on our service. Click on the link to continue. If it's not you, just ignore this message.",
            Mailbox.Transactional
        ),
        Email(
            "Please Confirm Subscription. If you received this email by mistake, simply delete it. You won't be subscribed if you don't click the confirmation link above.",
            Mailbox.Transactional
        ),
        Email(
            "Confirm Your Email. Please enter the code: 3849 or click below to activate your account.",
            Mailbox.Transactional
        ),
        Email("Dear CoolService user, We are very happy that you have chosen CoolService!", Mailbox.Transactional),
    )

    val nbc = emails.toNaiveBayesClassifier(
        featuresSelector = { it.words() },
        categorySelector = { it.mailbox },
    )

    nbc.predict("Thank you for signing up, Mike. We created this video to help you with first steps.".words()) shouldBe Mailbox.Transactional
    nbc.predict("Quick, quick, quick! Last change to get your zillion dollars today!".words()) shouldBe Mailbox.Advertisement
}

// Data

private val numericData get() = sequenceOf(1, 3, 5, 5, 1, 7, 2, 9, 1, 125)

private val repoData
    get() = sequenceOf(
        Project("compose-samples", Kotlin, 3_660),
        Project("gson", Java, 18_671),
        Project("kotlin", Kotlin, 33_730),
        Project("korge", Kotlin, 571),
        Project("ktor", Kotlin, 7_405),
        Project("RxJava", Java, 43_639),
        Project("scalatra", Scala, 2_472),
        Project("kotlin-statistics", Kotlin, 744),
    )

// Helpers

private fun <T> List<Pair<Range<Int>, T>>.prepareForComparison(): List<Pair<IntRange, T>> =
    map { (range, value) ->
        range.lowerBound..range.upperBound to value
    }

private fun Iterable<Project>.names() = map { it.name }

private data class Project(
    val name: String,
    val language: Language,
    val stars: Int,
)

private enum class Language {
    Kotlin, Java, Scala,
}

private enum class Mailbox { Transactional, Advertisement, Updates, }
private data class Email(val message: String, val mailbox: Mailbox)

private fun Email.words() = message.words()

// Thanks, Thomas!
// https://github.com/thomasnield/kotlin-statistics/blob/master/src/test/kotlin/org/nield/kotlinstatistics/NaiveBayesClassifierTest.kt#L126
private fun String.words() = split(Regex("\\s")).asSequence()
    .map { it.replace(Regex("[^A-Za-z]"), "").toLowerCase() }
    .filter { it.isNotEmpty() }
    .toSet()
