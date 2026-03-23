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

class ChangeVisibilityTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private val username: Username = Username("test");
    private val skillId = SkillId(1L);
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
    fun changeVisibility_UserHasAccessANdSkillExists_CallsChangeVisibilityOnRepository() {
        val visibility = Visibility.FRIENDS_ONLY

        handler.changeVisibility(username, skillId, visibility);

        verify(exactly = 1) { skillRepository.changeVisibility(skillId, visibility) }
    }

    @Test
    fun changeVisibility_SkillNotFound_ThrowsSkillNotFoundAndDoesNotCallChangeVisibilityOnRepository() {
        val visibility = Visibility.FRIENDS_ONLY
        every { skillRepository.getSkillById(skillId) } returns null

        assertThatThrownBy {
            handler.changeVisibility(username, skillId, visibility);
        } .isInstanceOf(SkillNotFoundException::class.java)

        verify(exactly = 0) { skillRepository.changeVisibility(skillId, visibility) }
    }

    @Test
    fun changeVisibility_UserIsNotAuthor_ThrowsAccessDeniedAndDoesNotCallChangeVisibilityOnRepository() {
        val visibility = Visibility.FRIENDS_ONLY

        assertThatThrownBy {
            handler.changeVisibility(Username("different"), skillId, visibility);
        } .isInstanceOf(SkillAccessDeniedException::class.java)

        verify(exactly = 0) { skillRepository.changeVisibility(skillId, visibility) }
    }
}