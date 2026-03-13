package com.skillhelper.application.userhandler

import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.UserHandler
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class GetProfileByNameTests {

    private lateinit var repository: IUserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var handler: UserHandler
    private lateinit var user: User;
    private lateinit var profile: Profile;
    private var username: Username = Username("test username");

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)

        profile = Profile("test bio", "test image")

        user = User(
            username = username,
            password = "test password",
            profile = profile,
        )
    }

    @Test
    fun getProfileByName_RepositoryReturnsValue_ReturnsCorrectValue() {
        val expected = profile

        every {
            repository.getUserByName(username)
        } returns user

        val actual = handler.getProfileByName(username)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getProfileByName_RepositoryReturnsNull_ReturnsNull() {
        every {
            repository.getUserByName(username)
        } returns null

        val actual = handler.getProfileByName(username)

        assertThat(actual).isNull()
    }

    @Test
    fun getProfileByName_CallsGetOnRepository() {
        every {
            repository.getUserByName(username)
        } returns null

        handler.getProfileByName(username)

        verify(exactly = 1) { repository.getUserByName(username) }
    }
}