package com.skillhelper.application.skillhandler

import com.skillhelper.application.implementations.SkillHandler
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddSkillTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private lateinit var mockSkill: Skill;
    private var username: Username = Username("test");

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        mockSkill = Skill(
            SkillId(1),
            "skill 1",
            "description 1",
            StressLevel(1),
            "test", username, Visibility.PUBLIC
        )
    }

    @Test
    fun addSkill_AuthorDoesNotExist_DoesNotCallAddSkillOnRepository() {
        every {
            userRepository.userExists(username)
        } returns false

        handler.addSkill(mockSkill)

        verify { skillRepository wasNot Called }
    }

    @Test
    fun addSkill_AuthorDoesNotExist_ReturnsNegativeOne() {
        val expected = -1L
        every {
            userRepository.userExists(username)
        } returns false

        val actual = handler.addSkill(mockSkill)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun addSkill_AuthorExists_CallsAddSkillOnRepository() {
        every {
            userRepository.userExists(username)
        } returns true

        handler.addSkill(mockSkill)

        verify(exactly = 1) { skillRepository.addSkill(mockSkill) }
    }

    @Test
    fun addSkill_AuthorExists_ReturnsRepositoryId() {
        val expected = SkillId(1L)
        every {
            userRepository.userExists(username)
        } returns true

        every {
            skillRepository.addSkill(mockSkill)
        } returns expected

        val actual = handler.addSkill(mockSkill)

        assertThat(actual).isEqualTo(expected)
    }
}