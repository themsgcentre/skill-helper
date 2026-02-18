package com.skillhelper.feature.entryhandler

import com.skillhelper.feature.implementations.EntryHandler
import com.skillhelper.feature.models.EntryDto
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class DeleteEntryTests {
    private lateinit var entryRepository: IEntryRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var handler: EntryHandler;

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);
    }

    @Test
    fun deleteEntry_CallsRepository() {
        val id = 1L;
        handler.deleteEntry(id)
        verify(exactly = 1) { 
            entryRepository.deleteEntry(id)
        }
    }
}