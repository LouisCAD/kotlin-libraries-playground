package framework.junit5

import framework.common.UseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UseCaseTest{
    @Test
    fun `it should return empty string`() {
        val useCase = UseCase()

        val actual = useCase.execute()
        val expected = ""

        assertEquals(expected, actual)
    }
}