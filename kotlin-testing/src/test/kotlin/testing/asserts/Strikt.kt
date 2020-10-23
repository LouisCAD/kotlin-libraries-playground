package testing.asserts

import testing.common.UseCase
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty

class Strikt {
    @Test
    fun `it should return empty string`() {
        val useCase = UseCase()

        val subject = useCase.execute()

        expectThat(subject).isEmpty()
    }
}
