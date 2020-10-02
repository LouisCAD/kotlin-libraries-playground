@file:Suppress("PackageDirectoryMismatch")

package playground.kodein.di

import org.kodein.di.*
import playground.shouldBe

/**
 * Kodein DI: painless Kotlin dependency injection
 *
 * - [Website](kodein.org/di/)
 * - [Github](https://github.com/Kodein-Framework/Kodein-DI)
 * - [CHANGELOG](https://github.com/Kodein-Framework/Kodein-DI/blob/master/CHANGELOG.md)
 */
fun main() {
    println()
    println("# Kotlin/org.kodein.di : Kotlin multiplatform / painless dependency injection")

    // A dependency container that provides a UserRepository for testing and a "real" one.
    val deps = DI {
        bind<UserRepository>(tag = "test") with singleton { MockedUserStore() }
        bind<UserRepository>() with singleton { InMemoryUserStore() }
    }

    val testApp by deps.newInstance { Application(instance(tag = "test")) }
    val realApp by deps.newInstance { Application(instance()) }

    val user = User(1)

    testApp.addUser(user)
    // In the test app, we want always the same user.
    testApp.getUser(1) shouldBe User(123)

    realApp.addUser(user)
    // IN the real app, we want the users we have previously stored.
    realApp.getUser(1) shouldBe user
}

/**
 * Abstraction of an application that can get and add users given a  UserRepository.
 */
internal class Application(private val userRepository: UserRepository) {
    fun getUser(id: Int): User? {
        return userRepository.userById(id)
    }

    fun addUser(user: User) {
        userRepository.addUser(user)
    }
}

/**
 * An interface that defines a user repository.
 */
interface UserRepository {
    fun addUser(user: User)
    fun userById(id: Int): User?
}

/**
 * A barebones user model.
 */
data class User(val id: Int)

/**
 * User repository that stores data in memory.
 */
internal class InMemoryUserStore : UserRepository {
    private val users: HashMap<Int, User> = HashMap()

    override fun addUser(user: User) {
        users[user.id] = user
    }

    override fun userById(id: Int): User? {
        return users[id]
    }
}

/**
 * User repository that always returns a user with id 123.
 */
internal class MockedUserStore : UserRepository {
    override fun addUser(user: User) {
    }

    override fun userById(id: Int): User? {
        return User(123)
    }
}
