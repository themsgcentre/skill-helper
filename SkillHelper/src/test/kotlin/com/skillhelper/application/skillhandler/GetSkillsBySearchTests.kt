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

class GetSkillsBySearchTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private lateinit var mockSkills: List<Skill>

    @BeforeEach
    fun setUp() {
        // TODO: add tests for visibility
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        mockSkills = listOf(
            Skill(SkillId(1), "skill 1", "description 1", StressLevel(1), null, Username("test"), Visibility.FRIENDS_ONLY),
            Skill(SkillId(2), "skill 2", "description 2", StressLevel(1), "test", Username("test 2"), Visibility.FRIENDS_ONLY),
        )
    }

    @Test
    fun getSkillsBySearch_NoSkills_ReturnsEmptyList() {
        every {
            skillRepository.getSkillsBySearch(any())
        } returns emptyList()

        val actual = handler.getSkillsBySearch(Username("test"), "test")

        assertThat(actual).isEmpty()
    }

    @Test
    fun getAllSkillsBySearch_HasSkills_ReturnsCorrectList() {
        every {
            skillRepository.getSkillsBySearch(any())
        } returns mockSkills

        val actual = handler.getSkillsBySearch(Username("test"), "test")

        assertThat(actual).isEqualTo(mockSkills)
    }
}