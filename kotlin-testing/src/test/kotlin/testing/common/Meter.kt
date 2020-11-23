package testing.common

typealias Meter = Long
typealias Kilometer = Double

class DistanceConverter {
    fun parse(distance: Meter): Kilometer {
        return when {
            distance in 0..999 -> distance.div(1000.0)
            distance in 1000..10_000 -> distance.div(1000.0)
            distance > 10_000 -> distance.div(1000.0)
            else -> throw UnsupportedOperationException()
        }
    }
}
