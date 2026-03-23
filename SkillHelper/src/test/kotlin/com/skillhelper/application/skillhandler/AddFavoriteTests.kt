package com.skillhelper.application.skillhandler

import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.SkillHandler
import com.skillhelper.application.throwables.SkillNotFoundException
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddFavoriteTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private val username = Username("test username");
    private val skillId = SkillId(1L);

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        every {
            userRepository.userExists(username)
        } returns true

        every {
            skillRepository.skillExists(skillId)
        } returns true

        every { favoriteRepository.getFavorites(username)} returns emptyList()
    }

    @Test
    fun addFavorite_ValidUserAndSkillIdAndNotAdded_CallsAddFavoriteOnRepository() {
        handler.addFavorite(username, skillId)

        verify(exactly = 1) { favoriteRepository.addFavorite(username, skillId) }
    }

    @Test
    fun addFavorite_UserDoesNotExist_ThrowsUserNotFoundAndDoesNotCallAddFavoriteOnRepository() {
        every { userRepository.userExists(username) } returns false

        assertThatThrownBy {
            handler.addFavorite(username, skillId)
        } .isInstanceOf(UserNotFoundException::class.java)

        verify(exactly = 0) { favoriteRepository.addFavorite(username, skillId) }
    }

    @Test
    fun addFavorite_SkillDoesNotExist_ThrowsSkillNotFoundAndDoesNotCallAddFavoriteOnRepository() {
        every { skillRepository.skillExists(any()) } returns false

        assertThatThrownBy {
            handler.addFavorite(username, skillId)
        } .isInstanceOf(SkillNotFoundException::class.java)

        verify(exactly = 0) { favoriteRepository.addFavorite(username, skillId) }
    }

    @Test
    fun addFavorite_SkillDoesNotExist_DoesNotCallAddFavoriteOnRepository() {
        every { favoriteRepository.getFavorites(username) } returns listOf(skillId)

        handler.addFavorite(username, skillId)

        verify(exactly = 0) { favoriteRepository.addFavorite(username, skillId) }
    }
}