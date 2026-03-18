package com.skillhelper.application.userhandler

import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.UserHandler
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.application.throwables.UsernameTakenException
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class UpdateUsernameTests {
    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private val oldName: Username = Username("old username");
    private val newName: Username = Username("new username");

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)

        every {
            repository.userExists(newName)
        } returns false
    }

    @Test
    fun updateUsername_NameAvailableAndUserExistsAndNamesDiffer_CallsUpdateOnRepository() {
        every { repository.userExists(oldName) } returns true
        every { repository.userExists(newName) } returns false

        handler.updateUsername(oldName, newName)
        verify(exactly = 1) { repository.updateUsername(oldName, newName) }
    }

    @Test
    fun updateUsername_NewNameIsOldName_DoesNotCallRepositoryWithoutException() {
        every { repository.userExists(oldName) } returns true
        handler.updateUsername(oldName, oldName)
        verify(exactly = 0) { repository.updateUsername(oldName, oldName) }
    }

    @Test
    fun updateUsername_NameNotAvailable_DoesNotCallRepositoryAndThrowsException() {
        every { repository.userExists(oldName) } returns true
        every { repository.userExists(newName) } returns true

        assertThatThrownBy {
            handler.updateUsername(oldName, newName)
        } .isInstanceOf(UsernameTakenException::class.java)


        verify(exactly = 0) { repository.updateUsername(oldName, newName) }
    }

    @Test
    fun updateUsername_UserDoesNotExist_DoesNotCallRepositoryAndThrowsException() {
        every { repository.userExists(newName) } returns false

        assertThatThrownBy {
            handler.updateUsername(oldName, newName)
        } .isInstanceOf(UserNotFoundException::class.java)


        verify(exactly = 0) { repository.updateUsername(oldName, newName) }
    }
}