@file:Suppress("PackageDirectoryMismatch")

package playground.sqldelight

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import playground.shouldBe
import util.AppDatabase
import util.Orders
import util.User
import java.util.*

/**
 *  SQLDelight - Generates typesafe Kotlin APIs from SQL
 *  - GitHub: https://github.com/cashapp/sqldelight
 *  - Official website: https://cashapp.github.io/sqldelight/
 *
 */

private val testUser by lazy { User(1L, "Jack") }
private val testOrders by lazy {
    listOf(
        Orders(1L, "McDonalds", Date().time, testUser.user_id_pk),
        Orders(2L, "Taco Bell", Date().time, testUser.user_id_pk),
        Orders(3L, "Shake Shack", Date().time, testUser.user_id_pk)
    )
}

fun main() {
    println()
    println("# cashapp/SQLDelight - Kotlin SQL Framework")

    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    AppDatabase.Schema.create(driver)
    val database = AppDatabase(driver)

    insertData(database)

    readData(database)

    deleteData(database)

}

fun insertData(database: AppDatabase) {
    val userQueries = database.userQueries
    val ordersQueries = database.ordersQueries

    userQueries.insertUser(testUser)

    testOrders.forEach { order ->
        ordersQueries.insertOrder(order)
    }

    println("Data Insert Successful")
}

fun readData(database: AppDatabase) {
    val userQueries = database.userQueries
    val ordersQueries = database.ordersQueries

    val userFromDB = userQueries.selectAllUsers().executeAsList().first()
    userFromDB shouldBe testUser

    val ordersFromDB = ordersQueries.selectOrderByUserID(testUser.user_id_pk).executeAsList()
    ordersFromDB shouldBe testOrders

    println("Read Data Successful")
}

fun deleteData(database: AppDatabase) {
    val userQueries = database.userQueries
    val ordersQueries = database.ordersQueries

    userQueries.deleteAllUsers()
    ordersQueries.deleteAllOrders()

    val usersFromDB = userQueries.selectAllUsers().executeAsList()
    val ordersFromDB = ordersQueries.selectOrderByUserID(testUser.user_id_pk).executeAsList()

    usersFromDB shouldBe emptyList()
    ordersFromDB shouldBe emptyList()

    println("Data Delete successful")
}



