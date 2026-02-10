package com.skillhelper.feature.userhandler

import com.skillhelper.feature.implementations.UserHandler
import com.skillhelper.feature.implementations.toDbo
import com.skillhelper.feature.models.UserDto
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class CreateUserTests {
    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private lateinit var dto: UserDto;
    private lateinit var username: String;

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)

        dto = UserDto(
            username = "test username",
            password = "test password",
            bio = "test bio",
            profileImage = "test image"
        )

        username = "test username"
    }

    @Test
    fun createUser_UserExists_DoesNotCallRepository() {
        every {
            repository.userExists(username)
        } returns true

        handler.createUser(dto);

        verify(exactly = 0) { repository.createUser(any()) }
    }

    @Test
    fun createUser_UserDoesNotExist_CallsRepository() {
        every {
            repository.userExists(username)
        } returns false

        val encodedPassword = "encoded password"

        every {
            encoder.encode(dto.password)
        } returns encodedPassword

        val expected = dto.toDbo(encodedPassword)

        handler.createUser(dto);

        verify(exactly = 1) { repository.createUser(expected) }
    }
}