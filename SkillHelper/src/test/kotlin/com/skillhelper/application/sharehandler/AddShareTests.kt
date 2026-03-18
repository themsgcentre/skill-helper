package com.skillhelper.application.sharehandler

import com.skillhelper.application.implementations.ShareHandler
import com.skillhelper.application.entities.Share
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.Username
import com.skillhelper.application.throwables.SkillNotFoundException
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Date
import java.time.Instant

class AddShareTests {
    private lateinit var userRepository: IUserRepository
    private lateinit var skillRepository: ISkillRepository
    private lateinit var shareRepository: IShareRepository
    private lateinit var handler: ShareHandler
    private val sender: Username = Username("test sender");
    private val receiver: Username = Username("test receiver");
    private val skillId: SkillId = SkillId(1L);
    private lateinit var mockShare: Share

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        shareRepository = mockk(relaxed = true)
        handler = ShareHandler(shareRepository, skillRepository, userRepository)

        every { userRepository.userExists(sender) } returns true
        every { userRepository.userExists(receiver) } returns true
        every { skillRepository.skillExists(skillId) } returns true

        mockShare = Share( null,
            sender, receiver, skillId, Date.from(Instant.now()),
        )
    }

    @Test
    fun addShare_SkillDoesNotExist_DoesNotCallShareRepositoryAndThrowsException() {
        every { skillRepository.skillExists(skillId) } returns false

        assertThatThrownBy {
            handler.addShare(mockShare)
        } .isInstanceOf(SkillNotFoundException::class.java)

        verify { shareRepository wasNot Called }
    }

    @Test
    fun addShare_SenderDoesNotExist_DoesNotCallShareRepositoryAndThrowsException() {
        every { userRepository.userExists(sender) } returns false

        assertThatThrownBy {
            handler.addShare(mockShare)
        } .isInstanceOf(UserNotFoundException::class.java)


        verify { shareRepository wasNot Called }
    }

    @Test
    fun addShare_ReceiverDoesNotExist_DoesNotCallShareRepositoryAndThrowsException() {
        every { userRepository.userExists(receiver) } returns false

        assertThatThrownBy {
            handler.addShare(mockShare)
        } .isInstanceOf(UserNotFoundException::class.java)

        verify { shareRepository wasNot Called }
    }

    @Test
    fun addShare_ValidShareData_CallsRepository() {
        handler.addShare(mockShare)

        verify(exactly = 1) { shareRepository.addShare(mockShare) }
    }
}