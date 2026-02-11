package com.skillhelper.feature.sharehandler

import com.skillhelper.feature.implementations.ShareHandler
import com.skillhelper.feature.implementations.toDto
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.models.ShareDbo
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
    private lateinit var mockShares: List<ShareDbo>

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        shareRepository = mockk(relaxed = true)
        handler = ShareHandler(shareRepository, skillRepository, userRepository)

        mockShares = listOf(
            ShareDbo(1L, "sender 1", "receiver 1", 1L, Date.from(Instant.now()), false),
            ShareDbo(2L, "sender 2", "receiver 2", 2L, Date.from(Instant.now()), false)
        )
    }

    @Test
    fun getAll_ReturnsCorrectListAndCallsRepository() {
        val username = "test"
        every { shareRepository.getAllForUser(username) } returns mockShares

        val usersByName = mapOf(
            "sender 1" to mockk<UserDbo>(relaxed = true) { every { profileImage } returns "p1" },
            "sender 2" to mockk<UserDbo>(relaxed = true) { every { profileImage } returns "p2" },
        )

        every { userRepository.getUserByName(any()) } answers {
            usersByName[firstArg()]
        }

        val skillsById = mapOf(
            1L to mockk<SkillDbo>(relaxed = true) { every { imageSrc } returns "s1" },
            2L to mockk<SkillDbo>(relaxed = true) { every { imageSrc } returns "s2" },
        )

        every { skillRepository.getSkillById(any()) } answers {
            skillsById[firstArg()]
        }

        val expected = mockShares.map { dbo ->
            val profileImg = usersByName[dbo.fromUser]?.profileImage
            val shareImg = skillsById[dbo.skill]?.imageSrc
            dbo.toDto(profileImg, shareImg)
        }

        val actual = handler.getAll(username)

        assertThat(actual).isEqualTo(expected)

        verify { shareRepository.getAllForUser(username) }
        verify(exactly = mockShares.size) { userRepository.getUserByName(any()) }
        verify(exactly = mockShares.size) { skillRepository.getSkillById(any()) }
    }
}