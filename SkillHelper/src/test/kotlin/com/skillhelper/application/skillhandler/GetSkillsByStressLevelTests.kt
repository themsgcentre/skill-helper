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

class GetSkillsByStressLevelTests {
    // TODO: add tests for visibility and more for level range
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private lateinit var mockSkills: List<Skill>

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        mockSkills = listOf(
            Skill(SkillId(1), "skill 1", "description 1", StressLevel(1), null, Username("test 1"), Visibility.FRIENDS_ONLY),
            Skill(SkillId(2), "skill 2", "description 2", StressLevel(2), "test", Username("test 2"), Visibility.FRIENDS_ONLY),
        )
    }

    @Test
    fun getSkillsByStressLevel_NoSkills_ReturnsEmptyList() {
        every {
            skillRepository.getSkillsByStressLevel(any(), any())
        } returns emptyList()

        val actual = handler.getSkillsByStressLevel(Username("test"), StressLevel(0), StressLevel(100))

        assertThat(actual).isEmpty()
    }

    @Test
    fun getSkillsByStressLevel_HasSkills_ReturnsCorrectList() {
        every {
            skillRepository.getSkillsByStressLevel(any(), any())
        } returns mockSkills

        val actual = handler.getSkillsByStressLevel(Username("test"), StressLevel(0), StressLevel(100))

        assertThat(actual).isEqualTo(mockSkills)
    }
}