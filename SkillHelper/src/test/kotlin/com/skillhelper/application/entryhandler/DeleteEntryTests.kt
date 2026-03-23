package com.skillhelper.application.entryhandler

import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.EntryHandler
import com.skillhelper.application.throwables.EntryAccessDeniedException
import com.skillhelper.application.throwables.EntryNotFoundException
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDateTime

class DeleteEntryTests {
    private lateinit var entryRepository: IEntryRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var handler: EntryHandler;
    private lateinit var mockEntry: Entry;
    private val username = Username("test");
    private val entryId = EntryId(1);

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);
        mockEntry = Entry(entryId, username, LocalDateTime.now(), "test", StressLevel(20) );
        every { entryRepository.getEntryById(entryId) } returns mockEntry;
    }

    @Test
    fun deleteEntry_UserHasRights_CallsRepository() {
        handler.deleteEntry(username, entryId)
        verify(exactly = 1) { 
            entryRepository.deleteEntry(entryId)
        }
    }

    @Test
    fun deleteEntry_UserHasNoRights_ThrowsDenyExceptionAndDoesNotCallRepository() {
        val other = Username("other");

        assertThatThrownBy {
            handler.deleteEntry(other, entryId)
        } .isInstanceOf(EntryAccessDeniedException::class.java)

        verify(exactly = 0) {
            entryRepository.deleteEntry(entryId)
        }
    }

    @Test
    fun deleteEntry_EntryNotFound_ThrowsNotFoundExceptionAndDoesNotCallRepository() {
        every { entryRepository.getEntryById(entryId) } returns null;

        assertThatThrownBy {
            handler.deleteEntry(username, entryId)
        } .isInstanceOf(EntryNotFoundException::class.java)

        verify(exactly = 0) {
            entryRepository.deleteEntry(entryId)
        }
    }
}