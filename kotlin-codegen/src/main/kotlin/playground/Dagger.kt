@file:Suppress("PackageDirectoryMismatch")

package playground.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import playground.shouldBe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Dagger: A fast dependency injector for Java and Android.
 *
 * - [Website](dagger.dev/)
 * - [Github](https://github.com/google/dagger)
 * - [CHANGELOG](https://github.com/google/dagger/blob/master/CHANGELOG.md)
 */
fun main() {
    println()
    println("# Kotlin/com.google.dagger : A fast dependency injector for Java and Android.")

    val testApp = DaggerTestApp.create().buildApp()
    val realApp = DaggerProductionApp.create().buildApp()

    val user = User(1)

    testApp.addUser(user)
    // In the test app, we want always the same user.
    testApp.getUser(1) shouldBe User(123)

    realApp.addUser(user)
    // IN the real app, we want the users we have previously stored.
    realApp.getUser(1) shouldBe user
}

/**
 * This component creates applications using the InMemoryStoreModule to provide dependencies.
 */
@Singleton
@Component(modules = [InMemoryStoreModule::class])
internal interface ProductionApp {
    fun buildApp(): Application
}

/**
 * This component creates applications using the MockedUserStoreModule to provide dependencies.
 */
@Singleton
@Component(modules = [MockedUserStoreModule::class])
internal interface TestApp {
    fun buildApp(): Application
}

/**
 * This module provides a singleton class of the InMemoryUserStore class.
 */
@Module
internal class InMemoryStoreModule {
    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return InMemoryUserStore()
    }
}

/**
 * This module provides a singleton class of the MockedUserStore class.
 */
@Module
internal class MockedUserStoreModule {
    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return MockedUserStore()
    }
}

/**
 * Abstraction of an application that can get and add users given a UserRepository.
 * The constructor is decorated with @Inject to allow Dagger to automatically create
 * and pass an instance of UserRepository.
 */
internal class Application @Inject constructor(private var userRepository: UserRepository) {
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

