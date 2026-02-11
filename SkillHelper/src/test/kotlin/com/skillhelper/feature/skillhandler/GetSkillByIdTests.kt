package com.skillhelper.feature.skillhandler

import com.skillhelper.feature.implementations.SkillHandler
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

class GetSkillByIdTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var visibilityRepository: IVisibilityRepository;
    private lateinit var handler: SkillHandler;

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        visibilityRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, visibilityRepository)
    }

    @Test
    fun getSkillById_SkillExists_ReturnsCorrectSkill() {
        val mockSkill = SkillDbo(1, "skill 1", "description 1", 1, null, 1, "src")
        every { skillRepository.getSkillById(mockSkill.id) } returns mockSkill

        val actual = handler.getSkillById(mockSkill.id)
        val expected = mockSkill.toDto()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getSkillById_SkillDosNotExist_ReturnsNull() {
        every { skillRepository.getSkillById(any()) } returns null

        val actual = handler.getSkillById(1)

        assertThat(actual).isNull()
    }
}