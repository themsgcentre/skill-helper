package com.skillhelper.application.sharehandler

import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.Share
import com.skillhelper.application.entities.ShareId
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.ShareHandler
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.models.SkillDbo
import com.skillhelper.repository.models.UserDbo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Date
import java.time.Instant
import kotlin.to

class GetAllTests {
    private lateinit var userRepository: IUserRepository
    private lateinit var skillRepository: ISkillRepository
    private lateinit var shareRepository: IShareRepository
    private lateinit var handler: ShareHandler
    private lateinit var mockShares: List<Share>

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        shareRepository = mockk(relaxed = true)
        handler = ShareHandler(shareRepository, skillRepository, userRepository)

        mockShares = listOf(
            Share(ShareId(1L), Username("sender 1"), Username("receiver 1"),
                SkillId(1L), Date.from(Instant.now()), false),
            Share(ShareId(2L), Username("sender 2"), Username("receiver 2"), SkillId(2L), Date.from(Instant.now()), false)
        )
    }

    @Test
    fun getAll_UserExists_ReturnsCorrectListAndCallsRepository() {
        val username = Username("test")
        every { shareRepository.getAllForUser(username) } returns mockShares
        every { userRepository.userExists(username) } returns true;

        val expected = mockShares

        val actual = handler.getAll(username)

        assertThat(actual).isEqualTo(expected)

        verify { shareRepository.getAllForUser(username) }
    }

    @Test
    fun getAll_UserDoesNotExist_ThrowsException() {
        val username = Username("test")
        every {
            userRepository.userExists(username)
        } returns false

        assertThatThrownBy {
            handler.getAll(username)
        } .isInstanceOf(UserNotFoundException::class.java)
    }
}