@file:Suppress("PackageDirectoryMismatch")

package playground.di.manual

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


/**
 * Manual Dependency Injection: Pure Kotlin, no framework required Dependency Injection
 * ideal for smaller-mid level projects which can be migrated easily to Dagger|Hilt like
 * framework.
 *
 * - [Blog](https://proandroiddev.com/hold-on-before-you-dagger-or-hilt-try-this-simple-di-f674c83ebeec)
 * - [Github-Sample](https://github.com/ch8n/android-no-3rd-party-mvvm-example)
 * - [Author](https://chetangupta.net)
 */
fun main() {
    println()
    println("# Manual dependency injection, written purely using Kotlin")

    val viewModel = Injector.viewModel
    viewModel.userCallback = { user ->
        if (user != null) {
            println(user)
        }
    }
    viewModel.error = { error ->
        println(error.stackTrace)
    }

    viewModel.getUser()
    // for migration guide to Hilt or Dagger, please refer to companion blog
}


/**
 * Resolver have provider function for construction of dependencies
 */
object Resolver {

    fun provideViewModel(sampleRepository: SampleRepository): ViewModel {
        return ViewModel(sampleRepository)
    }

    fun provideSampleRepository(
        localDataSource: DataSourceLocal,
        remoteDataSource: DataSourceRemote
    ): SampleRepository {
        return SampleRepositoryImpl(localDataSource, remoteDataSource)
    }

    fun provideLocalDataStore(localDB: LocalDatabaseService): DataSourceLocal {
        return DataSourceLocalImpl(localDB)
    }

    fun provideRemoteDataStore(apiService: ApiService): DataSourceRemote {
        return DataSourceRemoteImpl(apiService)
    }

    fun provideLocalDatabase() = object : LocalDatabaseService() {}

    fun provideApiService() = object : ApiService() {}
}

/**
 * Injector creates and according to the needs cache or creates dependencies
 * lazy can be used for caching and functions can be user to return new instances
 */
object Injector {
    // construct and cache remote source
    val apiService by lazy { Resolver.provideApiService() }
    val remoteSource by lazy { Resolver.provideRemoteDataStore(apiService) }

    // construct and cache local source
    val localDB by lazy { Resolver.provideLocalDatabase() }
    val localSource by lazy { Resolver.provideLocalDataStore(localDB) }

    // construct and cache sample Repository
    val sampleRepository by lazy { Resolver.provideSampleRepository(localSource, remoteSource) }

    // construct and cache View or you can use function to get new viewmodel per call
    val viewModel by lazy { Resolver.provideViewModel(sampleRepository) }
}


class ViewModel(private val sampleRepo: SampleRepository) {
    var error: (Exception) -> Unit = {}
    var userCallback: (SampleUser?) -> Unit = {}

    fun getUser() = runBlocking {
        //get cache value
        println("reading from cache")
        var user: SampleUser? = sampleRepo.getCacheUser()
        // get remote value
        if (user == null) {
            println("reading remote cache")
            val result = sampleRepo.getRemoteUser()
            user = when (result) {
                is Result.Success -> result.value
                is Result.Error -> {
                    error.invoke(result.error)
                    null
                }
            }
        }
        // get last local value
        if (user == null) {
            println("reading local cache")
            val result = sampleRepo.getLocalUser()
            user = when (result) {
                is Result.Success -> result.value
                is Result.Error -> {
                    error.invoke(result.error)
                    null
                }
            }
        }
        userCallback.invoke(user)
    }
}

class SampleRepositoryImpl(
    private val dataSourceLocal: DataSourceLocal,
    private val dataSourceRemote: DataSourceRemote
) : SampleRepository {

    private var user: SampleUser? = null

    override suspend fun getCacheUser(): SampleUser? {
        return user?.also { it.dataSource = DataSource.CACHE }
    }

    override suspend fun getRemoteUser(): Result<Exception, SampleUser> {
        return dataSourceRemote.fetchUser().also { result ->
            if (result is Result.Success) {
                dataSourceLocal.setUser(result.value)
            }
        }
    }

    override suspend fun getLocalUser(): Result<Exception, SampleUser> {
        return dataSourceLocal.getUser()
    }

}

class DataSourceLocalImpl(val localDB: LocalDatabaseService) : DataSourceLocal {

    override suspend fun getUser(): Result<Exception, SampleUser> {
        //simulate local db read
        delay(1000)
        return Result.build { SampleUser(dataSource = DataSource.LOCAL) }
    }

    override suspend fun setUser(user: SampleUser): Result<Exception, Unit> {
        //simulate local db write
        delay(1000)
        return Result.build { Unit }
    }

}

class DataSourceRemoteImpl(val apiService: ApiService) : DataSourceRemote {

    override suspend fun fetchUser(): Result<Exception, SampleUser> {
        //simulate network call
        delay(1000)
        return Result.build { SampleUser(dataSource = DataSource.REMOTE) }
    }

}

abstract class LocalDatabaseService {
    // add your db implementation
}

abstract class ApiService {
    // add your endpoint and network call implementation
}

interface DataSourceRemoteService {
    suspend fun fetchUser(): Result<Exception, SampleUser>
}

interface DataSourceLocalService {
    suspend fun getUser(): Result<Exception, SampleUser>
}

interface DataSourceRemote {
    suspend fun fetchUser(): Result<Exception, SampleUser>
}

interface DataSourceLocal {
    suspend fun getUser(): Result<Exception, SampleUser>
    suspend fun setUser(user: SampleUser): Result<Exception, Unit>
}

interface SampleRepository {
    suspend fun getCacheUser(): SampleUser?
    suspend fun getRemoteUser(): Result<Exception, SampleUser>
    suspend fun getLocalUser(): Result<Exception, SampleUser>
}


// --------- DTO/Entity -------
data class SampleUser(
    val name: String = "Chetan Gupta",
    val socialMedia: String = "twitter.com@ch8n2",
    val portfolio: String = "https://chetangupta.net",
    var dataSource: DataSource = DataSource.DEFAULT
)

enum class DataSource {
    DEFAULT, REMOTE, LOCAL, CACHE
}

// --------- Result Wrapper For Error Handling -------
sealed class Result<out E, out V> {

    data class Success<out V>(val value: V) : Result<Nothing, V>()
    data class Error<out E>(val error: E) : Result<E, Nothing>()

    companion object Factory {
        inline fun <V> build(function: () -> V): Result<Exception, V> = try {
            Success(function.invoke())
        } catch (e: kotlin.Exception) {
            Error(e)
        }
    }

}