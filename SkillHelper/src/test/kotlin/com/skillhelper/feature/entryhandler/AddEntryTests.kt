package com.skillhelper.feature.entryhandler

import com.skillhelper.feature.implementations.EntryHandler
import com.skillhelper.feature.implementations.toDbo
import com.skillhelper.feature.models.EntryDto
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class AddEntryTests {
    private lateinit var entryRepository: IEntryRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var handler: EntryHandler;
    private lateinit var testUser: String;
    private lateinit var testEntry: EntryDto;

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);

        testUser = "test"
        testEntry = EntryDto(0, testUser, LocalDateTime.now(), "test", 2);
    }

    @Test
    fun addEntry_UserDoesNotExist_DoesNotCallRepository() {
        every {
            userRepository.userExists(testUser)
        } returns false

        handler.addEntry(testEntry)

        verify {
            entryRepository wasNot Called
        }
    }

    @Test
    fun addEntry_UserExists_CallsRepository() {
        every {
            userRepository.userExists(testUser)
        } returns true

        handler.addEntry(testEntry)

        verify (exactly = 1) {
            entryRepository.addEntry(testEntry.toDbo())
        }
    }
}