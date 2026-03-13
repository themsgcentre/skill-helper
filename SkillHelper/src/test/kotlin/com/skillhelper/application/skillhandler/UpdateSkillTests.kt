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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateSkillTests {
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

        mockSkill = Skill(SkillId(1), "skill 1", "description 1", StressLevel(1), "test", Username("test 1"), Visibility.FRIENDS_ONLY)
    }

    @Test
    fun updateSkill_AuthorDoesNotExist_DoesNotCallUpdateSkillOnRepository() {
        every {
            userRepository.userExists(username)
        } returns false

        handler.updateSkill(mockSkill)

        verify { skillRepository wasNot Called }
    }

    @Test
    fun updateSkill_AuthorExists_CallsUpdateSkillOnRepository() {
        every {
            userRepository.userExists(username)
        } returns true

        handler.updateSkill(mockSkill)

        verify(exactly = 1) { skillRepository.updateSkill(mockSkill) }
    }
}