// This file is generated automatically with Kotlin Poet.
// More details at https://github.com/LouisCAD/kotlin-libraries-playground/blob/main/kotlin-jvm/src/main/kotlin/playground/KotlinPoet.kt
package references.kotlinPoet

import kotlin.Int
import kotlin.String

/**
 * Use this class for simple calculations.
 */
class Calculator(
    version: String
) {
    val model: String = """CALC-$version"""

    fun add(vararg values: Int): Int = values.reduce { acc, i -> acc + i }

    fun multiply(vararg values: Int): Int = values.reduce { acc, i -> acc * i }

    fun subtract(from: Int, amount: Int): Int = from - amount

    fun divide(value: Int, onto: Int): Int = value / onto

    infix fun Int.addTo(other: Int): Int = add(this, other)

    infix fun Int.multiplyOn(other: Int): Int = multiply(this, other)
}

fun main() {
    val c = Calculator(version = "1")

    c.testAddition()
    c.testMultiplication()
    c.testSubtraction()
    c.testDivision()
}

private fun Calculator.testAddition() {
    print("""[$model] Testing addition...""")
    try {
        check(add(3, 2, 5) == 10)
        check(add(5, 3, 2) == 10)
        check(3 addTo 5 addTo 2 == 10)
        check(5 addTo 3 addTo 2 == 10)

        println("OK")
    } catch (e: Throwable) {
        println("FAILED")
    }
}

private fun Calculator.testMultiplication() {
    print("""[$model] Testing multiplication...""")
    try {
        check(multiply(3, 2, 5) == 30)
        check(multiply(5, 3, 2) == 30)
        check(3 multiplyOn 5 multiplyOn 2 == 30)
        check(5 multiplyOn 3 multiplyOn 2 == 30)

        println("OK")
    } catch (e: Throwable) {
        println("FAILED")
    }
}

private fun Calculator.testSubtraction() {
    print("""[$model] Testing subtraction...""")
    try {
        check(subtract(3, 2) == 1)
        check(subtract(5, 3) == 2)

        println("OK")
    } catch (e: Throwable) {
        println("FAILED")
    }
}

private fun Calculator.testDivision() {
    print("""[$model] Testing division...""")
    try {
        check(divide(3, 2) == 1)
        check(divide(2, 3) == 0)

        println("OK")
    } catch (e: Throwable) {
        println("FAILED")
    }
}
