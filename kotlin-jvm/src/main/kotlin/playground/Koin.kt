@file:Suppress("PackageDirectoryMismatch")

package playground.di.koin

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import playground.shouldBe

/**
 * Koin: A pragmatic lightweight dependency injection framework for Kotlin developers.
 *
 * - [Website](https://insert-koin.io/)
 * - [Github](https://github.com/InsertKoinIO/koin)
 * - [CHANGELOG](https://github.com/InsertKoinIO/koin/blob/master/CHANGELOG.md)
 */
fun main() {
    println()
    println("# Kotlin/org.koin : A pragmatic lightweight dependency injection framework for Kotlin developers.")

    val deps = module {
        single(qualifier = named("production")) {
            InMemoryUserStore()
        } bind UserRepository::class
        single(qualifier = named("test")) {
            MockedUserStore()
        } bind UserRepository::class
    }

    startKoin {
        modules(deps)
    }

    val user = User(1)

    val testApp = Application("test")
    val realApp = Application("production")

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
internal class Application(environment: String) : KoinComponent {
    private val userRepository by inject<UserRepository>(qualifier = named(environment))

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
internal interface UserRepository {
    fun addUser(user: User)
    fun userById(id: Int): User?
}

/**
 * A barebones user model.
 */
internal data class User(val id: Int)

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
