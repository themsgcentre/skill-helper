package com.skillhelper.application.skillhandler

import com.skillhelper.application.implementations.DefaultSkillAccessPolicy
import com.skillhelper.application.implementations.SkillHandler
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.throwables.AuthorNotFoundException
import com.skillhelper.application.throwables.InvalidSkillOperationException
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddSkillTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private lateinit var mockSkill: Skill;
    private val username: Username = Username("test");
    private val skillId = SkillId(1L);

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(
            skillRepository,
            favoriteRepository,
            userRepository,
            DefaultSkillAccessPolicy(friendRepository),
        )

        mockSkill = Skill(
            skillId,
            "skill 1",
            "description 1",
            StressLevel(1),
            "test",
            username,
            Visibility.PUBLIC
        )
    }

    @Test
    fun addSkill_AuthorDoesNotExist_ThrowsAuthorNotFoundAndDoesNotCallAddSkillOnRepository() {
        every {
            userRepository.userExists(username)
        } returns false

        assertThatThrownBy {
            handler.addSkill(username, mockSkill)
        } .isInstanceOf(AuthorNotFoundException::class.java)

        verify { skillRepository wasNot Called }
    }

    @Test
    fun addSkill_AuthorDiffersFromSender_ThrowsInvalidOperationAndDoesNotCallAddSkillOnRepository() {
        every {
            userRepository.userExists(username)
        } returns true

        assertThatThrownBy {
            handler.addSkill(Username("different"), mockSkill)
        } .isInstanceOf(InvalidSkillOperationException::class.java)

        verify { skillRepository wasNot Called }
    }

    @Test
    fun addSkill_AuthorExists_CallsAddSkillOnRepository() {
        every {
            userRepository.userExists(username)
        } returns true

        handler.addSkill(username, mockSkill)

        verify(exactly = 1) { skillRepository.addSkill(mockSkill) }
    }

    @Test
    fun addSkill_AuthorExists_ReturnsRepositoryId() {
        val expected = skillId
        every {
            userRepository.userExists(username)
        } returns true

        every {
            skillRepository.addSkill(mockSkill)
        } returns expected

        val actual = handler.addSkill(username, mockSkill)

        assertThat(actual).isEqualTo(expected)
    }
}