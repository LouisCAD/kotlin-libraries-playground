@file:Suppress("PackageDirectoryMismatch")

package playground.apollo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.runBlocking
import launches.GetLaunchesQuery
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.util.concurrent.Executors


fun main() {
    println()
    println("# apollographql/Apollo - Typesafe GraphQL in Kotlin")

    val executorService = Executors.newCachedThreadPool()
    val dispatcher = Dispatcher(executorService)
    val okHttpClient = OkHttpClient.Builder().dispatcher(dispatcher).build()

    val apolloClient = ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .okHttpClient(okHttpClient)
        .dispatcher(executorService)
        .build()

    runBlocking {
        val response = try {
            apolloClient.query(GetLaunchesQuery()).await()
        } catch (e: ApolloException) {
            println("network error:")
            e.printStackTrace()
            return@runBlocking
        }

        response.data?.launches?.launches?.forEach {
            println("Mission ${it?.mission?.name} launched from site ${it?.site} ")
        }
    }

    /**
     * Shutdown any running threadpool to make the command terminate
     * (The JVM waits for all threads to terminate before exiting by default)
     */
    executorService.shutdown()
    dispatcher.executorService.shutdown()
}
