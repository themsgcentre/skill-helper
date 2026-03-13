package com.skillhelper.application.sharehandler

import com.skillhelper.application.entities.ShareId
import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.ShareHandler
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteShareTests {
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
    fun deleteShare_CallsRepository() {
        val shareId = ShareId(1L);
        val username = Username("test")
        handler.deleteShare(username, shareId)
        verify(exactly = 1) { shareRepository.deleteShare(shareId) }
    }
}