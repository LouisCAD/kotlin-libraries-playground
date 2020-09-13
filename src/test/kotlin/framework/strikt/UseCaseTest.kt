package me.rozkmin.testing.strikt

import framework.common.UseCase
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty

class UseCaseTest {
    @Test
    fun `it should return empty string`() {
        val useCase = UseCase()

        val subject = useCase.execute()

        expectThat(subject).isEmpty()
    }
}