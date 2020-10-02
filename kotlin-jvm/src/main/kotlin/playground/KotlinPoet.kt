@file:Suppress("PackageDirectoryMismatch")

package playground.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import playground.shouldBe


/**
 * Kotlin Poet
 *
 * [Website](https://square.github.io/kotlinpoet)
 * [GitHub](https://github.com/square/kotlinpoet)
 */

fun main() {
    println()
    println("# Kotlin Poet")

    val `package` = "references.kotlinPoet"

    val calculatorClass = ClassName(`package`, "Calculator")

    val generatedFile = FileSpec.builder(`package`, "calculator")
        .indent("    ")
        .addComment("""
            This file is generated automatically with Kotlin Poet.
            More details at https://github.com/LouisCAD/kotlin-libraries-playground/blob/main/kotlin-jvm/src/main/kotlin/playground/KotlinPoet.kt
        """.trimIndent())
        .appendCalculator(calculatorClass)
        .appendMainFunction()
        .appendTests(calculatorClass)
        .build()
        .toString()

    generatedFile shouldBe loadReferenceFile()
}

private fun FileSpec.Builder.appendCalculator(calculatorClass: ClassName) = apply {
    fun TypeSpec.Builder.appendAddFunction() =
        addFunction(FunSpec.builder("add")
            .addParameter("values", Int::class, KModifier.VARARG)
            .returns(Int::class)
            .addStatement("return values.reduce { acc, i -> acc + i }")
            .build())

    fun TypeSpec.Builder.appendMultiplyFunction() =
        addFunction(FunSpec.builder("multiply")
            .addParameter("values", Int::class, KModifier.VARARG)
            .returns(Int::class)
            .addStatement("return values.reduce { acc, i -> acc * i }")
            .build())

    fun TypeSpec.Builder.appendSubtractFunction() =
        addFunction(FunSpec.builder("subtract")
            .addParameter("from", Int::class)
            .addParameter("amount", Int::class)
            .returns(Int::class)
            .addStatement("return from - amount")
            .build())

    fun TypeSpec.Builder.appendDivideFunction() =
        addFunction(FunSpec.builder("divide")
            .addParameter("value", Int::class)
            .addParameter("onto", Int::class)
            .returns(Int::class)
            .addStatement("return value / onto")
            .build())

    fun TypeSpec.Builder.appendAddToFunction() =
        addFunction(FunSpec.builder("addTo")
            .addModifiers(KModifier.INFIX)
            .receiver(Int::class)
            .addParameter("other", Int::class)
            .returns(Int::class)
            .addStatement("return add(this, other)")
            .build())

    fun TypeSpec.Builder.appendMultiplyOnFunction() =
        addFunction(FunSpec.builder("multiplyOn")
            .addModifiers(KModifier.INFIX)
            .receiver(Int::class)
            .addParameter("other", Int::class)
            .returns(Int::class)
            .addStatement("return multiply(this, other)")
            .build())

    addType(TypeSpec.classBuilder(calculatorClass)
        .primaryConstructor(FunSpec.constructorBuilder()
            .addParameter("version", String::class)
            .build())
        .addProperty(PropertySpec.builder("model", String::class)
            .initializer("%P", "CALC-\$version")
            .build())
        .appendAddFunction()
        .appendMultiplyFunction()
        .appendSubtractFunction()
        .appendDivideFunction()
        .appendAddToFunction()
        .appendMultiplyOnFunction()
        .addKdoc("Use this class for simple calculations.")
        .build())
}

private fun FileSpec.Builder.appendMainFunction() = addFunction(
    FunSpec.builder("main")
        .addCode("""
                |val c = Calculator(version = "1")
                |
                |c.testAddition()
                |c.testMultiplication()
                |c.testSubtraction()
                |c.testDivision()
            """.trimMargin())
        .build())

private fun FileSpec.Builder.appendTests(calculatorClass: ClassName) = apply {
    fun buildTestFunction(
        operation: String,
        checks: List<String>,
    ): FunSpec = run {
        val functionName = "test${operation.capitalize()}"

        FunSpec.builder(functionName)
            .addModifiers(KModifier.PRIVATE)
            .receiver(calculatorClass)
            .addStatement("print(%P)", "[\$model] Testing $operation...")
            .beginControlFlow("try")
            .addCode(buildString {
                checks.forEach {
                    appendLine("check($it)")
                }
                appendLine()
            })
            .addStatement("println(%S)", "OK")
            .nextControlFlow("catch (e: Throwable)")
            .addStatement("println(%S)", "FAILED")
            .endControlFlow()
            .build()
    }

    addFunction(buildTestFunction(
        operation = "addition",
        checks = listOf(
            "add(3, 2, 5) == 10",
            "add(5, 3, 2) == 10",
            "3 addTo 5 addTo 2 == 10",
            "5 addTo 3 addTo 2 == 10",
        ),
    ))
    addFunction(buildTestFunction(
        operation = "multiplication",
        checks = listOf(
            "multiply(3, 2, 5) == 30",
            "multiply(5, 3, 2) == 30",
            "3 multiplyOn 5 multiplyOn 2 == 30",
            "5 multiplyOn 3 multiplyOn 2 == 30",
        ),
    ))
    addFunction(buildTestFunction(
        operation = "subtraction",
        checks = listOf(
            "subtract(3, 2) == 1",
            "subtract(5, 3) == 2",
        ),
    ))
    addFunction(buildTestFunction(
        operation = "division",
        checks = listOf(
            "divide(3, 2) == 1",
            "divide(2, 3) == 0",
        ),
    ))
}

private fun loadReferenceFile(): String {
    return Unit.javaClass.classLoader
        .getResource("references/kotlinPoet/calculator.kt")!!
        .readText()
}
