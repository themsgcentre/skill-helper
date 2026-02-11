package com.skillhelper.feature.skillhandler

import com.skillhelper.feature.implementations.SkillHandler
import com.skillhelper.feature.implementations.toDbo
import com.skillhelper.feature.models.SkillDto
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.interfaces.IVisibilityRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddSkillTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var visibilityRepository: IVisibilityRepository;
    private lateinit var handler: SkillHandler;
    private lateinit var mockSkill: SkillDto;

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        visibilityRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, visibilityRepository)

        mockSkill = SkillDto(1, "skill 1", "description 1", 1, "test", 2, "src")
    }

    @Test
    fun addSkill_AuthorDoesNotExist_DoesNotCallAddSkillOnRepository() {
        every {
            userRepository.userExists("test")
        } returns false

        handler.addSkill(mockSkill)

        verify { skillRepository wasNot Called }
    }

    @Test
    fun addSkill_AuthorDoesNotExist_ReturnsNegativeOne() {
        val expected = -1L
        every {
            userRepository.userExists("test")
        } returns false

        val actual = handler.addSkill(mockSkill)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun addSkill_AuthorExists_CallsAddSkillOnRepository() {
        every {
            userRepository.userExists("test")
        } returns true

        handler.addSkill(mockSkill)

        verify(exactly = 1) { skillRepository.addSkill(mockSkill.toDbo()) }
    }

    @Test
    fun addSkill_AuthorExists_ReturnsRepositoryId() {
        val expected = 1L
        every {
            userRepository.userExists("test")
        } returns true

        every {
            skillRepository.addSkill(mockSkill.toDbo())
        } returns expected

        val actual = handler.addSkill(mockSkill)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun addSkill_AuthorIsNull_CallsAddSkillOnRepository() {
        mockSkill = mockSkill.copy(author = null)

        handler.addSkill(mockSkill)

        verify(exactly = 1) { skillRepository.addSkill(mockSkill.toDbo()) }
    }

    @Test
    fun addSkill_AuthorIsNull_ReturnsRepositoryId() {
        val expected = 1L
        mockSkill = mockSkill.copy(author = null)

        every {
            skillRepository.addSkill(mockSkill.toDbo())
        } returns expected

        val actual = handler.addSkill(mockSkill)

        assertThat(actual).isEqualTo(expected)
    }
}