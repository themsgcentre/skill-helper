package com.skillhelper.application.skillhandler

import com.skillhelper.application.implementations.SkillHandler
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.throwables.InvalidSkillOperationException
import com.skillhelper.application.throwables.SkillAccessDeniedException
import com.skillhelper.application.throwables.SkillNotFoundException
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateSkillTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private lateinit var mockSkill: Skill;
    private val username: Username = Username("test");
    private val skillId  = SkillId(1L)

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        mockSkill = Skill(skillId, "skill 1", "description 1", StressLevel(1), "test", username, Visibility.FRIENDS_ONLY)

        every { skillRepository.getSkillById(skillId) } returns mockSkill;
        every {
            userRepository.userExists(username)
        } returns true
    }

    @Test
    fun updateSkill_AuthorDoesNotExist_ThrowsUserNotFoundAndDoesNotCallUpdateSkillOnRepository() {
        every {
            userRepository.userExists(username)
        } returns false

        assertThatThrownBy {
            handler.updateSkill(username, mockSkill)
        }

        verify { skillRepository wasNot Called }
    }

    @Test
    fun updateSkill_SenderNotAuthor_ThrowsAccessDeniedAndDoesNotCallUpdateSkillOnRepository() {
        val different = Username("different")
        every { userRepository.userExists(different) } returns true

        assertThatThrownBy {
            handler.updateSkill(different, mockSkill)
        } .isInstanceOf(SkillAccessDeniedException::class.java)

        verify(exactly = 0) { skillRepository.updateSkill(mockSkill) }
    }

    @Test
    fun updateSkill_AuthorUpdate_ThrowsAccessDeniedAndDoesNotCallUpdateSkillOnRepository() {
        assertThatThrownBy {
            handler.updateSkill(username, mockSkill.copy(author = Username("different")))
        } .isInstanceOf(InvalidSkillOperationException::class.java)

        verify(exactly = 0) { skillRepository.updateSkill(mockSkill) }
    }

    @Test
    fun updateSkill_SkillNotFound_ThrowsAccessDeniedAndDoesNotCallUpdateSkillOnRepository() {
        every { skillRepository.getSkillById(skillId) } returns null
        assertThatThrownBy {
            handler.updateSkill(username, mockSkill)
        } .isInstanceOf(SkillNotFoundException::class.java)

        verify(exactly = 0) { skillRepository.updateSkill(mockSkill) }
    }

    @Test
    fun updateSkill_AuthorExistsAndSenderIsAuthor_CallsUpdateSkillOnRepository() {
        handler.updateSkill(username, mockSkill)

        verify(exactly = 1) { skillRepository.updateSkill(mockSkill) }
    }
}