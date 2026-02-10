package com.skillhelper.feature.userhandler

import com.skillhelper.feature.implementations.UserHandler
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
    private lateinit var oldName: String;
    private lateinit var newName: String;

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)

        oldName = "old username"
        newName = "new username"

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