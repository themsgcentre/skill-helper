package com.skillhelper.application.sharehandler

import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.ShareHandler
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteAllForUserTests {
    private lateinit var userRepository: IUserRepository
    private lateinit var skillRepository: ISkillRepository
    private lateinit var shareRepository: IShareRepository
    private lateinit var handler: ShareHandler

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        shareRepository = mockk(relaxed = true)
        handler = ShareHandler(shareRepository, skillRepository, userRepository)
    }

    @Test
    fun deleteAllForUser_UserExists_CallsRepository() {
        val username = Username("test")
        every {
            userRepository.userExists(username)
        } returns true

        handler.deleteAllForUser(username)
        verify(exactly = 1) { shareRepository.deleteAllForUser(username) }
    }

    @Test
    fun deleteAllForUser_UserDoesNotExist_DoesNotCallRepositoryAndThrowsException()  {
        val username = Username("test")

        assertThatThrownBy {
            handler.deleteAllForUser(username)
        } .isInstanceOf(UserNotFoundException::class.java)

        verify(exactly = 0) { shareRepository.deleteAllForUser(username) }
    }
}