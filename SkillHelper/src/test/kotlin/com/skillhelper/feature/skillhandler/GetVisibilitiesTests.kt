package com.skillhelper.feature.skillhandler

import com.skillhelper.feature.implementations.SkillHandler
import com.skillhelper.feature.implementations.toDto
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.interfaces.IVisibilityRepository
import com.skillhelper.repository.models.VisibilityDbo
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetVisibilitiesTests {
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
    fun getVisibilities_ReturnsCorrectList() {
        val mockVisibilities = listOf(
            VisibilityDbo(1, "test 1"),
            VisibilityDbo(2, "test 2")
        )

        every {
            visibilityRepository.getAllVisibilityLevels()
        } returns mockVisibilities

        val expected = mockVisibilities.map { it.toDto() }

        val actual = handler.getVisibilities()

        assertThat(actual).isEqualTo(expected)
    }
}