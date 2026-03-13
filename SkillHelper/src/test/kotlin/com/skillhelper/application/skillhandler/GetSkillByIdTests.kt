package com.skillhelper.application.skillhandler

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.implementations.SkillHandler
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSkillByIdTests {
    // TODO: add tests for visibility
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)
    }

    @Test
    fun getSkillById_SkillExists_ReturnsCorrectSkill() {
        val mockSkill = Skill(SkillId(1), "skill 1", "description 1", StressLevel(1), null, Username("test"),
            Visibility.FRIENDS_ONLY)
        every { skillRepository.getSkillById(mockSkill.id!!) } returns mockSkill

        val actual = handler.getSkillById(Username(""), mockSkill.id!!)

        assertThat(actual).isEqualTo(mockSkill)
    }

    @Test
    fun getSkillById_SkillDosNotExist_ReturnsNull() {
        every { skillRepository.getSkillById(any()) } returns null

        val actual = handler.getSkillById(Username("test"), SkillId(1))

        assertThat(actual).isNull()
    }
}