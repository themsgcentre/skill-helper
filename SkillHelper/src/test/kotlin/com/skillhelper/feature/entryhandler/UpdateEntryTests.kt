package com.skillhelper.feature.entryhandler

import com.skillhelper.feature.implementations.EntryHandler
import com.skillhelper.feature.implementations.toDbo
import com.skillhelper.feature.models.EntryDto
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
    private lateinit var testUser: String;
    private var testId = 1L;
    private lateinit var testEntry: EntryDto;

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);

        testUser = "test"
        testEntry = EntryDto(testId, testUser, LocalDateTime.now(), "test", 2);
    }

    @Test
    fun updateEntry_UsernameIsOriginal_CallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry.toDbo()

        handler.updateEntry(testEntry)

        verify (exactly = 1) {
            entryRepository.updateEntry(testEntry.toDbo())
        }
    }

    @Test
    fun updateEntry_UsernameIsNotOriginal_CallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry.copy(username = "different").toDbo()

        handler.updateEntry(testEntry)

        verify (exactly = 0) {
            entryRepository.updateEntry(testEntry.toDbo())
        }
    }

    @Test
    fun updateEntry_EntryDoesNotExist_CallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns null

        handler.updateEntry(testEntry)

        verify (exactly = 1) {
            entryRepository.updateEntry(testEntry.toDbo())
        }
    }
}