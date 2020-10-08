package framework.junit5

import framework.common.DistanceConverter
import framework.common.Kilometer
import framework.common.Meter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*
import java.util.stream.Stream

class DistanceConverterParameterizedTest {
    @ParameterizedTest
    @ArgumentsSource(TestInputProvider::class)
    fun `check distance parser`(input: Input) {
        val distanceConverter = DistanceConverter()

        val actual = distanceConverter.parse(input.parameter)
        val expected = input.expected

        assertEquals(actual, expected)
    }


    data class Input(val parameter: Meter, val expected: Kilometer)


    class TestInputProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                    Input(500, 0.5),
                    Input(750, 0.8)
            ).map { Arguments.of(it) }
        }
    }
}
