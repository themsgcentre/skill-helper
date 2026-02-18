package com.skillhelper.feature.entryhandler

import com.skillhelper.feature.implementations.EntryHandler
import com.skillhelper.feature.implementations.toDbo
import com.skillhelper.feature.models.EntryDto
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
    private lateinit var testUser: String;
    private lateinit var testEntry: EntryDto;
    private var testId = 1L;

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);

        testUser = "test"
        testEntry = EntryDto(0, testUser, LocalDateTime.now(), "test", 2);
    }

    @Test
    fun getEntryById_EntryDoesNotExist_ReturnsNull() {
        every {
            entryRepository.getEntryById(testId)
        } returns null

        val actual = handler.getEntryById(testId)

        assertThat(actual).isNull()
    }

    @Test
    fun getEntryById_EntryDoesNotExist_CallsRepository() {
        every {
            entryRepository.getEntryById(testId)
        } returns null

        handler.getEntryById(testId)

        verify (exactly = 1) {
            entryRepository.getEntryById(testId)
        }
    }

    @Test
    fun getEntryById_EntryExist_ReturnsEntry() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry.toDbo()

        val actual = handler.getEntryById(testId)
        val expected = testEntry;

        assertThat(actual).isEqualTo(expected)
    }
}