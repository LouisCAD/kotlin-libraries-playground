@file:Suppress("PackageDirectoryMismatch")

package playground.apollo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.runBlocking
import launches.GetLaunchesQuery


fun main() {
  println()
  println("# apollographql/Apollo - Typesafe GraphQL in Kotlin")

  val apolloClient = ApolloClient.builder()
      .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
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
}