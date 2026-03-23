package com.skillhelper.application.userhandler

import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.UserHandler
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class UpdateBioTests {
    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private val username: Username = Username("test username");

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)
    }

    @Test
    fun updateUser_UserExists_CallsUpdateOnRepository() {
        val bio = "test bio"
        every {
            repository.userExists(username)
        } returns true

        handler.updateBio(username, bio)

        verify(exactly = 1) { repository.updateBio(username, bio) }
    }

    @Test
    fun updateUser_UserDoesNotExist_DoesNotCallRepositoryAndThrowsException() {
        val bio = "test bio"
        every {
            repository.userExists(username)
        } returns false

        assertThatThrownBy {
            handler.updateBio(username, bio)
        } .isInstanceOf(UserNotFoundException::class.java)

        verify(exactly = 0) { repository.updateBio(any(), any()) }
    }
}