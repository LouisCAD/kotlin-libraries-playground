package testing.asserts

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import testing.common.UseCase

class Strikt {
    @Test
    fun `it should return empty string`() {
        val useCase = UseCase()

        val subject = useCase.execute()

        expectThat(subject).isEmpty()
    }
}
