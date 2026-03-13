package com.skillhelper.application.entryhandler

import com.skillhelper.application.implementations.EntryHandler
import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
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
    private var testUser: Username = Username("test");
    private lateinit var testEntry: Entry;

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);

        testEntry = Entry(EntryId(0), testUser, LocalDateTime.now(), "test", StressLevel(2));
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
            entryRepository.addEntry(testEntry)
        }
    }
}