package com.skillhelper.application.sharehandler

import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.Share
import com.skillhelper.application.entities.ShareId
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.ShareHandler
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.models.SkillDbo
import com.skillhelper.repository.models.UserDbo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
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
    fun getAll_ReturnsCorrectListAndCallsRepository() {
        val username = Username("test")
        every { shareRepository.getAllForUser(username) } returns mockShares

        val usersByName = mapOf(
            "sender 1" to mockk<User>(relaxed = true) { every { profile } returns Profile("bio1", "img1") },
            "sender 2" to mockk<User>(relaxed = true) { every { profile } returns Profile("bio1", "img1") },
        )

        every { userRepository.getUserByName(any()) } answers {
            usersByName[firstArg()]
        }

        val skillsById = mapOf(
            1L to mockk<Skill>(relaxed = true) { every { imageSrc } returns "s1" },
            2L to mockk<Skill>(relaxed = true) { every { imageSrc } returns "s2" },
        )

        every { skillRepository.getSkillById(any()) } answers {
            skillsById[firstArg()]
        }

        val expected = mockShares

        val actual = handler.getAll(username)

        assertThat(actual).isEqualTo(expected)

        verify { shareRepository.getAllForUser(username) }
        verify(exactly = mockShares.size) { userRepository.getUserByName(any()) }
        verify(exactly = mockShares.size) { skillRepository.getSkillById(any()) }
    }
}