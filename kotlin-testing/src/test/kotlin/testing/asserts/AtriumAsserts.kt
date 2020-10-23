package testing.asserts

import ch.tutteli.atrium.api.fluent.en_GB.isEmpty
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import testing.common.UseCase

class AtriumAsserts {

    @Test
    fun `should return empty string`() {
        val useCase = UseCase()

        expect(useCase.execute()).isEmpty()
    }
}
