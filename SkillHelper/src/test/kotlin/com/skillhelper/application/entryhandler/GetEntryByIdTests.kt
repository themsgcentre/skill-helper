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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetEntryByIdTests {
    private lateinit var entryRepository: IEntryRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var handler: EntryHandler;
    private var username: Username = Username("test");
    private lateinit var testEntry: Entry;
    private var testId = EntryId(1L);

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);

        testEntry = Entry(testId, username, LocalDateTime.now(), "test", StressLevel(2));
    }

    @Test
    fun getEntryById_EntryDoesNotExist_ReturnsNull() {
        every {
            entryRepository.getEntryById(testId)
        } returns null

        val actual = handler.getEntryById(username, testId)

        assertThat(actual).isNull()
    }

    @Test
    fun getEntryById_EntryDoesNotExist_CallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns null

        handler.getEntryById(username, testId)

        verify (exactly = 1) {
            entryRepository.getEntryById(testId)
        }
    }

    @Test
    fun getEntryById_EntryExist_ReturnsEntry() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry

        val actual = handler.getEntryById(username, testId)
        val expected = testEntry;

        assertThat(actual).isEqualTo(expected)
    }
}