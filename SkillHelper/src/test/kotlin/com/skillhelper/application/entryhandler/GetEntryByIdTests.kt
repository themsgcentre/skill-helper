package com.skillhelper.application.entryhandler

import com.skillhelper.application.implementations.EntryHandler
import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.throwables.EntryAccessDeniedException
import com.skillhelper.application.throwables.EntryNotFoundException
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetEntryByIdTests {
    private lateinit var entryRepository: IEntryRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var handler: EntryHandler;
    private val username: Username = Username("test");
    private lateinit var testEntry: Entry;
    private val testId = EntryId(1L);

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);

        testEntry = Entry(testId, username, LocalDateTime.now(), "test", StressLevel(2));
    }

    @Test
    fun getEntryById_EntryDoesNotExist_ThrowsNotFoundException() {
        every {
            entryRepository.getEntryById(testId)
        } returns null

        assertThatThrownBy {
            handler.getEntryById(username, testId)
        } .isInstanceOf(EntryNotFoundException::class.java)
    }

    @Test
    fun getEntryById_UserHasNoAccess_ThrowsAccessDeniedException() {
        every {
            entryRepository.getEntryById(testId)
        } returns testEntry

        assertThatThrownBy {
            handler.getEntryById(Username("other"), testId)
        } .isInstanceOf(EntryAccessDeniedException::class.java)
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