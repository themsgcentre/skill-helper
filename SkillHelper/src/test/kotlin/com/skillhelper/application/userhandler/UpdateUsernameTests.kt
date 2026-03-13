package com.skillhelper.application.userhandler

import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.UserHandler
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class UpdateUsernameTests {
    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private var oldName: Username = Username("old username");
    private var newName: Username = Username("new username");

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
    fun updateUsername_NameAvailableAndNotOldName_CallsUpdateOnRepository() {
        handler.updateUsername(oldName, newName)
        verify(exactly = 1) { repository.updateUsername(oldName, newName) }
    }

    @Test
    fun updateUsername_NewNameIsOldName_DoesNotCallRepository() {
        handler.updateUsername(oldName, oldName)
        verify(exactly = 0) { repository.updateUsername(any(), any()) }
    }

    @Test
    fun updateUsername_NameNotAvailable_DoesNotCallRepository() {
        every {
            repository.userExists(newName)
        } returns true

        handler.updateUsername(oldName, newName)

        verify(exactly = 0) { repository.updateUsername(any(), any()) }
    }
}