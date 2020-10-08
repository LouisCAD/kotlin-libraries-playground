package playground

import kotlinx.serialization.Serializable

@Serializable
data class HttpbinGet(
        val args: Map<String, String> = emptyMap(),
        val headers: Map<String, String> = emptyMap(),
        val origin: String,
        val url: String
)
