package com.skillhelper.feature.skillhandler

import com.skillhelper.feature.implementations.SkillHandler
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

class AddFavoriteTests {
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

        every {
            userRepository.userExists(any())
        } returns true

        every {
            skillRepository.skillExists(any())
        } returns true
    }

    @Test
    fun addFavorite_ValidUserAndSkillId_CallsAddFavoriteOnRepository() {
        val username = "test"
        val skillId = 1L;

        handler.addFavorite(username, skillId)

        verify(exactly = 1) { favoriteRepository.addFavorite(username, skillId) }
    }

    @Test
    fun addFavorite_UserDoesNotExist_DoesNotCallAddFavoriteOnRepository() {
        every { userRepository.userExists(any()) } returns false

        handler.addFavorite("test", 1)

        verify { favoriteRepository wasNot Called }
    }

    @Test
    fun addFavorite_SkillDoesNotExist_DoesNotCallAddFavoriteOnRepository() {
        every { skillRepository.skillExists(any()) } returns false

        handler.addFavorite("test", 1)

        verify { favoriteRepository wasNot Called }
    }
}