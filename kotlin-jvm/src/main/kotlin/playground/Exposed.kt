@file:Suppress("PackageDirectoryMismatch")

package playground.exposed

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.Month

/**
 * JetBrains/Exposed - Kotlin SQL Framework
 *
 * - [GitHub](https://github.com/JetBrains/Exposed)
 */
fun main() {
    println()
    println("# JetBrains/Exposed - Kotlin SQL Framework")

    Database.connect("jdbc:h2:mem:playground")

    transaction {
        SchemaUtils.create(Users, Orders)

        insertData()

        queryData()

        deleteData()
    }
}

fun insertData() {
    val jasper = User.new {
        name = "Jasper"
        age = 21
    }
    Order.new {
        reference = "JBE0000001"
        product = "Pepperoni Pizza"
        orderedAt = LocalDateTime.of(2020, Month.OCTOBER, 1, 18, 0)
        user = jasper
    }
    Order.new {
        reference = "JBE0000002"
        product = "Chicken Wings"
        orderedAt = LocalDateTime.of(2020, Month.OCTOBER, 1, 19, 0)
        user = jasper
    }

    val frank = User.new {
        name = "Frank"
        age = 25
    }
    Order.new {
        reference = "JBE0000010"
        product = "Cheeseburger"
        orderedAt = LocalDateTime.of(2020, Month.OCTOBER, 1, 19, 0)
        user = frank
    }
}

fun queryData() {
    println("All users:")
    val users = User.all()
    users.forEach { println("User { id = ${it.id}, name = ${it.name}, age = ${it.age} }") }
    println()

    println("Jasper's orders:")
    val jasper = User.find { Users.name eq "Jasper" }.single()
    jasper.orders.forEach { println(" * Order { id = ${it.id}, reference = ${it.reference}, product = ${it.product}, orderedAt = ${it.orderedAt} }") }
    println()

    println("Order with reference 'JBE0000010':")
    val order = Order.find { Orders.reference eq "JBE0000010" }.single()
    println("Order { id = ${order.id}, product = ${order.product}, orderedAt = ${order.orderedAt}, user = ${order.user.name} }")
    println()
}

fun deleteData() {
    var amountOfOrders = Order.count()
    println("Amount of orders: $amountOfOrders")

    println("Deleting order 2...")
    Order[2].delete()

    amountOfOrders = Order.count()
    println("Amount of orders: $amountOfOrders")
}

object Users : IntIdTable() {
    val name = varchar("name", 100)
    val age = integer("age")
}

object Orders : IntIdTable() {
    val reference = varchar("reference", 10).uniqueIndex()
    val product = varchar("product", 100)
    val orderedAt = datetime("ordered_at")
    val userId = reference("user_id", Users)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var age by Users.age
    val orders by Order referrersOn Orders.userId
}

class Order(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Order>(Orders)

    var reference by Orders.reference
    var product by Orders.product
    var orderedAt by Orders.orderedAt
    var user by User referencedOn Orders.userId
}
