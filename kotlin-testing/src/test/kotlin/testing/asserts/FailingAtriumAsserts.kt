package testing.asserts

import ch.tutteli.atrium.api.fluent.en_GB.isEmpty
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import testing.common.UseCase

class FailingAtriumAsserts {

    @Test
    fun `failing assertion group`() {
        expect(17) {
            isLessThan(4)
            isGreaterThan(20)
            isEqualComparingTo(17)
        }
    }
}
