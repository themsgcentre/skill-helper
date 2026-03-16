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

class UpdateProfilePictureTests {
    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private var username: Username = Username("test username");

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)
    }

    @Test
    fun updateProfilePicture_UserExists_CallsUpdateOnRepository() {
        val imageSrc = "test src";
        every {
            repository.userExists(username)
        } returns true
        handler.updateProfilePicture(username, imageSrc);

        verify(exactly = 1) { repository.updateProfilePicture(username, imageSrc) }
    }

    @Test
    fun updateProfilePicture_UserDoesNotExist_DoesNotCallRepositoryAndThrowsException() {
        val imageSrc = "test src";
        every {
            repository.userExists(username)
        } returns false

        assertThatThrownBy {
            handler.updateProfilePicture(username, imageSrc);
        } .isInstanceOf(UserNotFoundException::class.java)

        verify(exactly = 0) { repository.updateProfilePicture(username, imageSrc) }
    }
}