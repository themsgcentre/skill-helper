package com.skillhelper.feature.userhandler

import com.skillhelper.feature.implementations.UserHandler
import com.skillhelper.feature.implementations.toProfileDto
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.models.UserDbo
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
    private lateinit var dbo: UserDbo;
    private lateinit var username: String;

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        encoder = mockk(relaxed = true)
        handler = UserHandler(repository, encoder)

        dbo = UserDbo(
            username = "test username",
            password = "test password",
            bio = "test bio",
            profileImage = "test image"
        )

        username = "test username"
    }

    @Test
    fun getProfileByName_RepositoryReturnsValue_ReturnsCorrectValue() {
        val expected = dbo.toProfileDto()

        every {
            repository.getUserByName(username)
        } returns dbo

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
    fun getProfileByName_CallsRepository() {
        every {
            repository.getUserByName(username)
        } returns null

        handler.getProfileByName(username)

        verify(exactly = 1) { repository.getUserByName(username) }
    }
}