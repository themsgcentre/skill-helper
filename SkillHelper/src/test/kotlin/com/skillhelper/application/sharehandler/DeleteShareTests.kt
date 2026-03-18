package com.skillhelper.application.sharehandler

import com.skillhelper.application.entities.Share
import com.skillhelper.application.entities.ShareId
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.ShareHandler
import com.skillhelper.application.throwables.ShareAccessDeniedException
import com.skillhelper.application.throwables.ShareNotFoundException
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Date
import java.time.Instant

class DeleteShareTests {
    private lateinit var userRepository: IUserRepository
    private lateinit var skillRepository: ISkillRepository
    private lateinit var shareRepository: IShareRepository
    private lateinit var handler: ShareHandler
    private lateinit var mockShare: Share;
    private var shareId = ShareId(1);
    private val forUser = Username("test receiver")
    private val fromUser = Username("test sender")
    private val skillId = SkillId(1);

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        shareRepository = mockk(relaxed = true)
        handler = ShareHandler(shareRepository, skillRepository, userRepository)
        mockShare = Share(shareId, forUser, fromUser, skillId, Date.from(Instant.now()), false)

        every {
            shareRepository.getShareById(shareId)
        } returns mockShare
    }

    @Test
    fun deleteShare_UserIsReceiver_CallsRepository() {
        handler.deleteShare(forUser, shareId)
        verify(exactly = 1) { shareRepository.deleteShare(shareId) }
    }

    @Test
    fun deleteShare_UserIsSender_CallsRepository() {
        handler.deleteShare(fromUser, shareId)
        verify(exactly = 1) { shareRepository.deleteShare(shareId) }
    }

    @Test
    fun deleteShare_UserHasNoRights_ThrowsDenyExceptionAndDoesNotCallRepository() {
        val other = Username("other")

        assertThatThrownBy {
            handler.deleteShare(other, shareId)
        } .isInstanceOf(ShareAccessDeniedException::class.java)

        verify(exactly = 0) { shareRepository.deleteShare(any()) }
    }

    @Test
    fun deleteShare_ShareDoesNotExist_ThrowsNotFoundExceptionAndDoesNotCallRepository() {
        every { shareRepository.getShareById(shareId) } returns null

        assertThatThrownBy {
            handler.deleteShare(forUser, shareId)
        } .isInstanceOf(ShareNotFoundException::class.java)

        verify(exactly = 0) { shareRepository.deleteShare(any()) }
    }
}