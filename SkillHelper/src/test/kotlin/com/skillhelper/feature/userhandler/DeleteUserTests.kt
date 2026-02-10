package com.skillhelper.feature.userhandler

import com.skillhelper.feature.implementations.UserHandler
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class DeleteUserTests {
    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private lateinit var username: String;

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)

        username = "test username"
    }

    @Test
    fun deleteUser_CallsDeleteOnRepository() {
        handler.deleteUser(username)
        verify(exactly = 1) { repository.deleteUser(username) }
    }
}