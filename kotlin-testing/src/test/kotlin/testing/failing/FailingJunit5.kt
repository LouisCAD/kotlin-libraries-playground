package testing.failing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import testing.common.DistanceConverter
import testing.common.Kilometer
import testing.common.Meter
import java.util.stream.Stream

class FailingJunit5ParameterizedTest {
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
                Input(500, 0.6),
                Input(750, 0.8)
            ).map { Arguments.of(it) }
        }
    }
}
