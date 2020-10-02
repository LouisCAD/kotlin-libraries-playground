@file:Suppress("PackageDirectoryMismatch")

package playground.ktor.client

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import playground.ktor.client.Network.API_URL
import playground.shouldBe
import retrofit2.*
import retrofit2.http.QueryMap

/**
 * Ktor Http Clients

- [Website](https://ktor.io/docs/clients-index.html)
- [GitHub ktorio/ktor](https://github.com/ktorio/ktor)
 */
fun main() {
    println()
    println("# Ktor Http client")
    val api: KtorHttpbinApi = KtorHttpbinApi(Network.ktorClient)

    runBlocking {
        val response = api.get("hello" to "world")
        response.args shouldBe mapOf("hello" to "world")
        response.url shouldBe "http://httpbin.org/get?hello=world"
    }
    Network.ktorClient.close()
}

class KtorHttpbinApi(
        val client: HttpClient
) {

    suspend fun get(vararg params: Pair<String, String>) =
            client.get<HttpbinGet>("$API_URL/get") {
                params.forEach {
                    url.parameters.append(it.first, it.second)
                }
            }
}

object Network {
    var API_URL = "http://httpbin.org"

    val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            .build()

    val ktorClient =  HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = defaultSerializer()
        }
        engine {
            preconfigured = okHttpClient
        }
    }
}

@Serializable
data class HttpbinGet(
        val args: Map<String, String> = emptyMap(),
        val headers: Map<String, String> = emptyMap(),
        val origin: String,
        val url: String
)
