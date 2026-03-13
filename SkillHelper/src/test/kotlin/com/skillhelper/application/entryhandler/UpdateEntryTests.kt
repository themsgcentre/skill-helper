package com.skillhelper.application.entryhandler

import com.skillhelper.application.implementations.EntryHandler
import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UpdateEntryTests {
    private lateinit var entryRepository: IEntryRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var handler: EntryHandler;
    private var username: Username = Username("test");
    private var testId = EntryId(1L);
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
    fun updateEntry_UsernameIsNotOriginal_CallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry.copy(user = Username("different"))

        handler.updateEntry(Username("different"), testEntry)

        verify (exactly = 0) {
            entryRepository.updateEntry(any())
        }
    }
}