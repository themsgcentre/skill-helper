package com.skillhelper.application.userhandler

import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.UserHandler
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class UpdateBioTests {
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
    fun updateUser_CallsUpdateOnRepository() {
        val bio = "test bio"
        handler.updateBio(username, bio)

        verify(exactly = 1) { handler.updateBio(username, bio) }
    }
}