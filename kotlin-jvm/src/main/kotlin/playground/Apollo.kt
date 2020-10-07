@file:Suppress("PackageDirectoryMismatch")

package playground.apollo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.runBlocking
import launches.GetLaunchesQuery
import okhttp3.OkHttpClient
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor


fun main() {
  println()
  println("# apollographql/Apollo - Typesafe GraphQL in Kotlin")

  val okHttpClient = OkHttpClient()
  val dispatcher = Executors.newCachedThreadPool()

  val apolloClient = ApolloClient.builder()
      .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
      .okHttpClient(okHttpClient)
      .dispatcher(dispatcher)
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
  dispatcher.shutdown()
  okHttpClient.dispatcher.executorService.shutdown()
}