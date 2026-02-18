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

class GetEntriesTests {
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
    fun getEntries_ReturnsList() {
        val expected = listOf(testEntry);

        every {
            entryRepository.getEntries(testUser)
        } returns listOf(testEntry.toDbo())

        val actual = handler.getEntries(testUser);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    fun getEntries_CallsRepository() {
        handler.getEntries(testUser);
        verify(exactly = 1) { entryRepository.getEntries(testUser) }
    }
}