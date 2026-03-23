package com.skillhelper.application.skillhandler

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.implementations.SkillHandler
import com.skillhelper.application.throwables.SkillAccessDeniedException
import com.skillhelper.application.throwables.SkillNotFoundException
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteSkillTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private val username: Username = Username("test")
    private val skillId = SkillId(1L)
    private lateinit var mockSkill: Skill;

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        mockSkill = Skill(
            skillId,
            "skill 1",
            "description 1",
            StressLevel(1),
            "test",
            username,
            Visibility.PUBLIC
        )

        every {
            skillRepository.getSkillById(skillId)
        } returns mockSkill
    }

    @Test
    fun deleteSkill_SkillExistsAndUserIsAuthor_CallsDeleteSkillOnRepository() {
        handler.deleteSkill(username, skillId);

        verify(exactly = 1) { skillRepository.deleteSkill(skillId) }
    }

    @Test
    fun deleteSkill_SkillDoesNotExist_ThrowsSkillNotFoundAndDoesNotCallDeleteOnRepository() {
        every { skillRepository.getSkillById(skillId) } returns null

        assertThatThrownBy {
            handler.deleteSkill(username, skillId)
        } .isInstanceOf(SkillNotFoundException::class.java)

        verify(exactly = 0) { skillRepository.deleteSkill(skillId) }
    }

    @Test
    fun deleteSkill_UserNotAuthor_ThrowsAccessDeniedAndDoesNotCallDeleteOnRepository() {
        assertThatThrownBy {
            handler.deleteSkill(Username("different"), skillId)
        } .isInstanceOf(SkillAccessDeniedException::class.java)

        verify(exactly = 0) { skillRepository.deleteSkill(skillId) }
    }
}