package framework.atrium

import ch.tutteli.atrium.api.fluent.en_GB.isEmpty
import ch.tutteli.atrium.api.verbs.expect
import framework.common.UseCase
import org.junit.Test

class UseCaseTest {
    @Test
    fun `should return empty string`() {
        val useCase = UseCase()

        expect(useCase.execute()).isEmpty()
    }
}