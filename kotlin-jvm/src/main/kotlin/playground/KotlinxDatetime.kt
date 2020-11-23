@file:Suppress("PackageDirectoryMismatch")

package playground.kotlinx.datetime

import kotlinx.datetime.*
import playground.shouldBe
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.time.ExperimentalTime
import kotlin.time.days
import kotlin.time.hours


fun main() {
    println("")
    println("# kotlinx.datetime - multiplatform date/time library")

    playWithInstants()
    playWithLocalDateAndTime()
    playWithJavaInterop()
}

private fun playWithInstants() {
    // `Instant` is just a point in UTC-SLS time (SLS stands for Smoothed Leap Seconds)

    // We can get current system UTC time point with `Clock.System`
    run {
        val rightNow = Clock.System.now()
        Thread.sleep(100L)
        val aBitLater = Clock.System.now()
        // and compare them
        check(aBitLater > rightNow) { "System clock should make tick-tock" }
    }

    // We also can use our own implementation for `Clock` (e.g. for testing purposes)
    run {
        val brokenClock = object : Clock {
            private val stuckInTime = Clock.System.now()

            override fun now() = stuckInTime
        }
        val rightNow = brokenClock.now()
        Thread.sleep(100L)
        val aBitLater = brokenClock.now()
        // now it's always the same time
        aBitLater shouldBe rightNow
    }

    // We can export `Instant` to ISO representation
    run {
        val rightNow = Clock.System.now()

        val isoString = rightNow.toString()
        check(isISOCompliant(isoString))

        // and parse it back
        val parsedInstant = Instant.parse(isoString)
        parsedInstant shouldBe rightNow
    }

    @OptIn(ExperimentalTime::class)
    // We can just back and forth on time line
    run {
        val rightNow = Clock.System.now()

        val oneHourAfter = rightNow + 1.hours
        val tenDaysBefore = rightNow - 1.days

        // and compare them
        check(tenDaysBefore < rightNow && rightNow < oneHourAfter)

        val timeSpread = oneHourAfter - tenDaysBefore
        timeSpread shouldBe (1.days + 1.hours)
    }

    // We can use bigger time-frames too
    run {
        val timestamp = "2020-10-01T06:00:00Z"
        val someDay = Instant.parse(timestamp)

        // forth
        val sameDayInFiveYears = someDay.plus(5, DateTimeUnit.YEAR, TimeZone.UTC)
        sameDayInFiveYears.toString() shouldBe "2025-10-01T06:00:00Z"

        // and back
        val sameDayLastCentury = someDay.plus(-1, DateTimeUnit.CENTURY, TimeZone.UTC)
        sameDayLastCentury.toString() shouldBe "1920-10-01T06:00:00Z"

        // All values are immutable
        someDay.toString() shouldBe timestamp
    }

    // We can compare two timestamps
    run {
        val day1 = Instant.parse("2020-10-01T06:00:00Z")
        val day2 = Instant.parse("2020-12-04T06:00:00Z")

        // Use `DateTimePeriod` instead of `Duration` for big time scales (months, years etc)
        val timePassed = (day2 - day1).toDateTimePeriod()

        // ATTENTION!!!

        // Unfortunately (yet?) we can't compare like this (first value is in hours, second - in months + days)
        //timePassed shouldBe DateTimePeriod(months = 2, days = 3)  // Will not work!

        // Just look
        timePassed.let {
            it.months shouldBe 0
            it.days shouldBe 0
            it.hours shouldBe 1536
        }

        DateTimePeriod(months = 2, days = 3).let {
            it.months shouldBe 2
            it.days shouldBe 3
            it.hours shouldBe 0
        }
    }

    // In those cases when you need UNIX timestamp
    run {
        val timestamp = Instant.parse("2020-10-01T06:00:00Z")

        timestamp.epochSeconds shouldBe 1601532000
        timestamp.toEpochMilliseconds() shouldBe 1601532000_000L
    }

    // And let me show you some special values
    run {
        val longLongTimeAgo = Instant.DISTANT_PAST
        longLongTimeAgo.isDistantPast shouldBe true

        val longLongTimeAfter = Instant.DISTANT_FUTURE
        longLongTimeAfter.isDistantFuture shouldBe true
    }
}

private fun playWithLocalDateAndTime() {
    // When we need moment in time with certain time zone - we can use `LocalDateTime`
    run {
        val timestamp = Instant.parse("2020-10-01T06:00:00Z")

        val timeZone = TimeZone.of("UTC+4")  // You can use values like `"Asia/Tbilisi"` too
        val localDateTime = timestamp.toLocalDateTime(timeZone)

        // ATTENTION! `LocalDateTime` knows nothing about used timezone
        localDateTime.toString() shouldBe "2020-10-01T10:00"

        // So you need to remember represented timezone
        localDateTime.toInstant(timeZone) shouldBe timestamp

        // Or else you'll get wrong values
        val anotherTimeZone = TimeZone.of("UTC+2")
        localDateTime.toInstant(anotherTimeZone) shouldBe Instant.parse("2020-10-01T08:00:00Z")
    }

    // If you don't need time component - use `LocalDate`
    run {
        val localDate = LocalDate.parse("2020-10-01")
        localDate shouldBe LocalDate(
            year = 2020,
            month = Month.OCTOBER,
            dayOfMonth = 1,
        )
    }

    // You can convert `LocalDate` and `LocalDateTime` back and forth
    run {
        val birthDay = LocalDate(
            year = 2020,
            month = Month.OCTOBER,
            dayOfMonth = 1,
        )

        // to `LocalDateTime`
        val partyTime = birthDay.atTime(
            hour = 16,
            minute = 30,
        )
        partyTime shouldBe LocalDateTime(
            year = 2020,
            month = Month.OCTOBER,
            dayOfMonth = 1,
            hour = 16,
            minute = 30,
        )

        // to `LocalDate`
        val partyDay = partyTime.date
        partyDay shouldBe birthDay
    }
}

private fun playWithJavaInterop() {
    // You can use `java.time.*` classes on JVM target if needed
    run {
        val hacktoberfestStart = LocalDate(
            year = 2020,
            month = Month.OCTOBER,
            dayOfMonth = 1,
        ).atStartOfDayIn(TimeZone.UTC)

        // Kotlin Instant --> Java Instant
        val javaInstant = hacktoberfestStart.toJavaInstant()
        javaInstant.toString() shouldBe hacktoberfestStart.toString()
        javaInstant::class.qualifiedName shouldBe "java.time.Instant"

        // Java Instant --> Kotlin Instant
        val kotlinInstant = javaInstant.toKotlinInstant()
        kotlinInstant shouldBe Instant.parse("2020-10-01T00:00:00Z")
    }
}

private fun isISOCompliant(timestamp: String): Boolean =
    try {
        DateTimeFormatter.ISO_DATE_TIME.parse(timestamp)
        true
    } catch (_: DateTimeParseException) {
        false
    }
