package com.skillhelper.feature.skillhandler

import com.skillhelper.feature.implementations.SkillHandler
import com.skillhelper.feature.implementations.UserHandler
import com.skillhelper.feature.implementations.toDto
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.interfaces.IVisibilityRepository
import com.skillhelper.repository.models.SkillDbo
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetAllSkillsTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var visibilityRepository: IVisibilityRepository;
    private lateinit var handler: SkillHandler;
    private lateinit var mockSkills: List<SkillDbo>

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        visibilityRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, visibilityRepository)

        mockSkills = listOf(
            SkillDbo(1, "skill 1", "description 1", 1, null, 1, null),
            SkillDbo(2, "skill 2", "description 2", 1, "test", 2, "src"),
        )
    }

    @Test
    fun getAllSkills_NoSkills_ReturnsEmptyList() {
        every {
            skillRepository.getAllSkills()
        } returns emptyList()

        val actual = handler.getAllSkills()

        assertThat(actual).isEmpty()
    }

    @Test
    fun getAllSkills_HasSkills_ReturnsCorrectList() {
        every {
            skillRepository.getAllSkills()
        } returns mockSkills

        val actual = handler.getAllSkills()
        val expected = mockSkills.map{skill -> skill.toDto()}

        assertThat(actual).isEqualTo(expected)
    }
}