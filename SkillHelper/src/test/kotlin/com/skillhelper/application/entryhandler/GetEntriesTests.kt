package com.skillhelper.application.entryhandler

import com.skillhelper.application.implementations.EntryHandler
import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.throwables.UserNotFoundException
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

class GetEntriesTests {
    private lateinit var entryRepository: IEntryRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var handler: EntryHandler;
    private val testUser: Username = Username("test");
    private lateinit var testEntry: Entry;

    @BeforeEach
    fun setUp() {
        entryRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        handler = EntryHandler(entryRepository, userRepository);

        testEntry = Entry(EntryId(0), testUser, LocalDateTime.now(), "test", StressLevel(2));

        every { userRepository.userExists(testUser) } returns true
    }

    @Test
    fun getEntries_UserExists_ReturnsList() {
        val expected = listOf(testEntry);

        every {
            entryRepository.getEntries(testUser)
        } returns listOf(testEntry)

        val actual = handler.getEntries(testUser);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    fun getEntries_UserExists_CallsRepository() {
        handler.getEntries(testUser);
        verify(exactly = 1) { entryRepository.getEntries(testUser) }
    }

    @Test
    fun getEntries_UserDoesNotExist_ThrowsExceptionAndDoesNotCallRepository() {
        every { userRepository.userExists(testUser) } returns false
        assertThatThrownBy {
            handler.getEntries(testUser);
        } .isInstanceOf(UserNotFoundException::class.java)

        verify(exactly = 0) { entryRepository.getEntries(testUser) }
    }
}