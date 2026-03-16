package com.skillhelper.application.userhandler

import com.skillhelper.application.implementations.UserHandler
import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.application.throwables.UsernameTakenException
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder

class CreateUserTests {
    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private lateinit var user: User;
    private var username: Username = Username("test username");

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)

        user = User(
            username = username,
            password = "test password",
            Profile(
                bio = "test bio",
                profileImage = "test image"
            )
        )
    }

    @Test
    fun createUser_UsernameTaken_DoesNotCallRepositoryAndThrowsException() {
        every {
            repository.userExists(username)
        } returns true

        assertThatThrownBy {
            handler.createUser(username, "password", Profile("bio", "img"));
        } .isInstanceOf(UsernameTakenException::class.java)

        verify(exactly = 0) { repository.createUser(any()) }
    }

    @Test
    fun createUser_UsernameAvailable_CallsCreateOnRepository() {
        every {
            repository.userExists(username)
        } returns false

        val encodedPassword = "encoded password"

        every {
            encoder.encode(user.password)
        } returns encodedPassword

        val expected = user.copy(password = encoder.encode(user.password))

        handler.createUser(username, user.password, user.profile);

        verify(exactly = 1) { repository.createUser(expected) }
    }
}