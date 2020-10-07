package framework.atrium

import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import framework.common.DistanceConverter
import framework.common.Kilometer
import framework.common.Meter
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class DistanceConverterParameterizedTest {
    @ParameterizedTest
    @ArgumentsSource(TestInputProvider::class)
    internal fun checkDistanceParser(input: Input) {
        val distanceConverter = DistanceConverter()

        val actual = distanceConverter.parse(input.parameter)
        val expected = input.expected

        // import atrium.api.verbs
        expect(expected).toBe(actual)
    }


    internal data class Input(val parameter: Meter, val expected: Kilometer)

    internal class TestInputProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(Input(5000, 5.0), Input(7500, 75.0))
                .map { Arguments.of(it) }
        }

    }
}