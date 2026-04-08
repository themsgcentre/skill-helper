package com.skillhelper.application.userhandler

import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.UserHandler
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.security.crypto.password.PasswordEncoder

class UsernameExistsTests {
    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private val username: Username = Username("test_username");

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)
    }

    @Test
    fun userExists_CallsRepository() {
        handler.userExists(username)
        verify(exactly = 1) { repository.userExists(username) }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun userExists_ReturnsRepositoryValue(expected: Boolean) {
        every { repository.userExists(username) } returns expected

        val actual = handler.userExists(username)

        assertThat(actual).isEqualTo(expected)
    }
}