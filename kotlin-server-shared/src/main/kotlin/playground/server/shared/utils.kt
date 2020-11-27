package playground.server.shared

import java.util.*

fun <T> Optional<T>.nullable() : T? =
    if (isEmpty) null else get()
