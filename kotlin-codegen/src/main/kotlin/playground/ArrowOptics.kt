@file:Suppress("PackageDirectoryMismatch")

package playground.arrow.optics

import arrow.core.ListK
import arrow.core.Tuple2
import arrow.core.k
import arrow.core.toT
import arrow.optics.Iso
import arrow.optics.Optional
import arrow.optics.dsl.every
import arrow.optics.extensions.listk.each.each
import arrow.optics.optics
import playground.shouldBe

/**
 * Λrrow - Functional companion to Kotlin's Standard Library
 *
 * [GitHub](https://github.com/arrow-kt/arrow)
 * [Official Website](https://arrow-kt.io/docs/optics/dsl/)
 */
fun main() {
    println()
    println("# arrow-kt/arrow-core")
    println("Λrrow - Functional companion to Kotlin's Standard Library")
    println()


    val john = Employee("John Doe", Company("Kategory", Address("Functional city", Street(42, "lambda street"))))
    val optional: Optional<Employee, String> = Employee.company.address.street.name
    val JOHN = optional.modify(john, String::toUpperCase)
    JOHN shouldBe Employee(
        name = "John Doe",
        company = Company(
            name = "Kategory",
            address = Address(city = "Functional city", street = Street(number = 42, name = "LAMBDA STREET"))
        )
    )

    val jane = Employee("Jane Doe", Company("Kategory", Address("Functional city", Street(42, "lambda street"))))
    val employees = Employees(listOf(john, jane).k())
    val transformed = Employees.employees.every(ListK.each())
        .company.address.street.name
        .modify(employees, String::toUpperCase)
    transformed shouldBe Employees(
        listOf(
            Employee(
                name = "John Doe",
                company = Company(
                    name = "Kategory",
                    address = Address(city = "Functional city", street = Street(number = 42, name = "LAMBDA STREET"))
                )
            ),
            Employee("Jane Doe", Company("Kategory", Address("Functional city", Street(42, "LAMBDA STREET"))))
        ).k()
    )

    val pointIsoTuple: Iso<Point2D, Tuple2<Int, Int>> = Iso(
        get = { point -> point.x toT point.y },
        reverseGet = { tuple -> Point2D(tuple.a, tuple.b) }
    )

    val point = Point2D(6, 10)
    pointIsoTuple.get(point) shouldBe Tuple2(6, 10)

}

data class Point2D(val x: Int, val y: Int)


@optics
data class Employees(val employees: ListK<Employee>) {
    companion object
}

@optics
data class Street(val number: Int, val name: String) {
    companion object
}

@optics
data class Address(val city: String, val street: Street) {
    companion object
}

@optics
data class Company(val name: String, val address: Address) {
    companion object
}

@optics
data class Employee(val name: String, val company: Company?) {
    companion object
}
