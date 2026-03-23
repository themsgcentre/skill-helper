package com.skillhelper.application.entryhandler

import com.skillhelper.application.implementations.EntryHandler
import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.throwables.EntryAccessDeniedException
import com.skillhelper.application.throwables.InvalidEntryOperationException
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UpdateEntryTests {
    private lateinit var entryRepository: IEntryRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var handler: EntryHandler;
    private val username: Username = Username("test");
    private val testId = EntryId(1L);
    private lateinit var testEntry: Entry;

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);

        testEntry = Entry(testId, username, LocalDateTime.now(), "test", StressLevel(2));
    }

    @Test
    fun updateEntry_UsernameIsOriginal_CallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry

        handler.updateEntry(username, testEntry)

        verify (exactly = 1) {
            entryRepository.updateEntry(testEntry)
        }
    }

    @Test
    fun updateEntry_FalseSender_ThrowsAccessDeniedExceptionAndDoesNotCallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry

        assertThatThrownBy {
            handler.updateEntry(Username("different"), testEntry)
        } .isInstanceOf(EntryAccessDeniedException::class.java)

        verify (exactly = 0) {
            entryRepository.updateEntry(any())
        }
    }

    @Test
    fun updateEntry_UsernameNotOriginal_ThrowsInvalidOperationExceptionAndDoesNotCallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry

        assertThatThrownBy {
            handler.updateEntry(username, testEntry.copy(user = Username("different")))
        } .isInstanceOf(InvalidEntryOperationException::class.java)

        verify (exactly = 0) {
            entryRepository.updateEntry(any())
        }
    }
}