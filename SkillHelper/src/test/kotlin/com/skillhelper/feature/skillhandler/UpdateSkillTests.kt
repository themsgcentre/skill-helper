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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateSkillTests {
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
    fun updateSkill_AuthorDoesNotExist_DoesNotCallUpdateSkillOnRepository() {
        every {
            userRepository.userExists("test")
        } returns false

        handler.updateSkill(mockSkill)

        verify { skillRepository wasNot Called }
    }

    @Test
    fun updateSkill_AuthorExists_CallsUpdateSkillOnRepository() {
        every {
            userRepository.userExists("test")
        } returns true

        handler.updateSkill(mockSkill)

        verify(exactly = 1) { skillRepository.updateSkill(mockSkill.toDbo()) }
    }

    @Test
    fun updateSkill_AuthorIsNull_CallsUpdateSkillOnRepository() {
        mockSkill = mockSkill.copy(author = null)

        handler.updateSkill(mockSkill)

        verify(exactly = 1) { skillRepository.updateSkill(mockSkill.toDbo()) }
    }
}