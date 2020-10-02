@file:Suppress("PackageDirectoryMismatch")

package playground.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import playground.shouldBe
import retrofit2.*
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * square/retrofit - A type-safe HTTP client for Android and the JVM

- [Website](https://square.github.io/retrofit/)
- [GitHub square/retrofit](https://github.com/square/retrofit)
- [CHANGELOG](https://github.com/square/retrofit/blob/master/CHANGELOG.md)
- [Consuming APIs with Retrofit | CodePath Android Cliffnotes](https://guides.codepath.com/android/Consuming-APIs-with-Retrofit#references)
 */
fun main() {
    println()
    println("# square/retrofit - A type-safe HTTP client for Android and the JVM")
    val api: RetrofitHttpbinApi = Network.retrofit.create()
    val response = api.get(mapOf("hello" to "world")).execute()
    response.isSuccessful shouldBe true
    response.body()!!.run {
        args shouldBe mapOf("hello" to "world")
        url shouldBe "http://httpbin.org/get?hello=world"
    }
}

interface RetrofitHttpbinApi {

    @GET("get")
    fun get(@QueryMap params: Map<String, String>): Call<HttpbinGet>
}

@Serializable
data class HttpbinGet(
        val args: Map<String, String> = emptyMap(),
        val headers: Map<String, String> = emptyMap(),
        val origin: String,
        val url: String
)

object Network {
    var API_URL = "http://httpbin.org/"

    val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .build()

    val contentType = "application/json".toMediaType()

    @OptIn(ExperimentalSerializationApi::class)
    val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_URL)
            //.addCallAdapterFactory(CallAdapter.Factory)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
}
