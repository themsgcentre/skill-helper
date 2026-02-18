package com.skillhelper.repository

import com.skillhelper.repository.helpers.insertEntry
import com.skillhelper.repository.helpers.insertUser
import com.skillhelper.repository.implementations.EntryRepository
import com.skillhelper.repository.models.EntryDbo
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntryRepositoryTests {
    @Autowired
    lateinit var repository: EntryRepository

    @Autowired
    lateinit var jdbc: JdbcClient

    @BeforeEach
    fun setUp() {
        jdbc.sql("""DELETE FROM dbo.[Entry];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @AfterAll
    fun tearDown() {
        jdbc.sql("""DELETE FROM dbo.[Entry];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @Test
    fun getEntries_UserDoesNotExist_ReturnsEmptyList() {
        val actual = repository.getEntries("missingUser")
        assertThat(actual).isEmpty()
    }

    @Test
    fun getEntries_UserHasEntries_ReturnsCorrectList() {
        val username = "user1"
        insertUser(jdbc, username)

        val t1 = LocalDateTime.of(2026, 2, 11, 10, 15, 30)
        val t2 = LocalDateTime.of(2026, 2, 11, 18, 45, 5)

        val e1 = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 10,
            time = t1
        )
        val e2 = EntryDbo(
            id = 0L,
            username = username,
            text = "second",
            stressLevel = 20,
            time = t2
        )

        val id1 = insertEntry(jdbc, e1)
        val id2 = insertEntry(jdbc, e2)

        val expected = listOf(
            e1.copy(id = id1),
            e2.copy(id = id2)
        )

        val actual = repository.getEntries(username)

        assertWithTime(actual, expected)
    }

    @Test
    fun getEntryById_IdDoesNotExist_ReturnsNull() {
        val actual = repository.getEntryById(9999L)

        assertThat(actual).isNull()
    }

    @Test
    fun getEntryById_IdExists_ReturnsCorrectEntry() {
        val username = "user1"
        insertUser(jdbc, username)

        val time = LocalDateTime.of(2026, 2, 11, 14, 30, 15)

        val entry = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 42,
            time = time
        )

        val id = insertEntry(jdbc, entry)

        val actual = repository.getEntryById(id)

        assertWithTime(listOf(actual!!), listOf(entry.copy(id = id)))
    }

    @Test
    fun addEntry_ValidEntry_AddsEntry() {
        val username = "user1"
        insertUser(jdbc, username)

        val entry = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 10,
            time = LocalDateTime.of(2026, 2, 11, 12, 0, 0)
        )

        val newId = repository.addEntry(entry)

        val actual = repository.getEntryById(newId)
        val expected = entry.copy(id = newId)

        assertWithTimeSingle(actual!!, expected);
    }

    @Test
    fun addEntry_UserDoesNotExist_ThrowsForeignKeyException() {
        val entry = EntryDbo(
            id = 0L,
            username = "missingUser",
            text = "first",
            stressLevel = 10,
            time = LocalDateTime.of(2026, 2, 11, 12, 0, 0)
        )

        assertThatThrownBy { repository.addEntry(entry) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addEntry_StressLevelBelowZero_ThrowsException() {
        val username = "user1"
        insertUser(jdbc, username)

        val entry = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = -1,
            time = LocalDateTime.of(2026, 2, 11, 12, 0, 0)
        )

        assertThatThrownBy { repository.addEntry(entry) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addEntry_StressLevelAboveHundred_ThrowsException() {
        val username = "user1"
        insertUser(jdbc, username)

        val entry = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 101,
            time = LocalDateTime.of(2026, 2, 11, 12, 0, 0)
        )

        assertThatThrownBy { repository.addEntry(entry) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateEntry_ValidData_UpdatesEntry() {
        val username = "user1"
        insertUser(jdbc, username)

        val original = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 10,
            time = LocalDateTime.of(2026, 2, 11, 10, 0, 0)
        )

        val id = insertEntry(jdbc, original)

        val updated = EntryDbo(
            id = id,
            username = username,
            text = "updated",
            stressLevel = 55,
            time = LocalDateTime.of(2026, 2, 11, 18, 30, 0)
        )

        repository.updateEntry(updated)

        val actual = repository.getEntryById(id)

        assertWithTimeSingle(actual, updated)
    }

    @Test
    fun updateEntry_StressLevelBelowZero_ThrowsException() {
        val username = "user1"
        insertUser(jdbc, username)

        val original = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 10,
            time = LocalDateTime.of(2026, 2, 11, 10, 0, 0)
        )

        val id = insertEntry(jdbc, original)

        val invalid = original.copy(
            id = id,
            stressLevel = -1
        )

        assertThatThrownBy { repository.updateEntry(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateEntry_StressLevelAboveHundred_ThrowsException() {
        val username = "user1"
        insertUser(jdbc, username)

        val original = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 10,
            time = LocalDateTime.of(2026, 2, 11, 10, 0, 0)
        )

        val id = insertEntry(jdbc, original)

        val invalid = original.copy(
            id = id,
            stressLevel = 101
        )

        assertThatThrownBy { repository.updateEntry(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun deleteEntry_ExistingEntry_RemovesIt() {
        val username = "user1"
        insertUser(jdbc, username)

        val entry = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 10,
            time = LocalDateTime.of(2026, 2, 11, 10, 0, 0)
        )

        val id = insertEntry(jdbc, entry)

        repository.deleteEntry(id)

        val actual = repository.getEntryById(id)

        assertThat(actual).isNull()
    }

    @Test
    fun deleteEntry_IdDoesNotExist_DoesNothing() {
        val username = "user1"
        insertUser(jdbc, username)

        val entry = EntryDbo(
            id = 0L,
            username = username,
            text = "first",
            stressLevel = 10,
            time = LocalDateTime.of(2026, 2, 11, 10, 0, 0)
        )

        val id = insertEntry(jdbc, entry)

        repository.deleteEntry(9999L)

        val actual = repository.getEntryById(id)

        assertWithTimeSingle(actual, entry.copy(id = id))
    }

    private fun assertWithTimeSingle(actual: EntryDbo?, expected: EntryDbo) {
        assertThat(actual).isNotNull

        val a = actual!!

        assertThat(a)
            .usingRecursiveComparison()
            .ignoringFields("time")
            .isEqualTo(expected)

        assertThat(a.time.truncatedTo(ChronoUnit.SECONDS))
            .isEqualTo(expected.time.truncatedTo(ChronoUnit.SECONDS))
    }

    private fun assertWithTime(actual: List<EntryDbo>, expected: List<EntryDbo>) {
        assertThat(actual)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("time")
            .containsExactlyInAnyOrderElementsOf(expected)

        actual.forEach { a ->
            val e = expected.first { it.id == a.id }

            val actualTime = a.time.truncatedTo(ChronoUnit.SECONDS)
            val expectedTime = e.time.truncatedTo(ChronoUnit.SECONDS)

            assertThat(actualTime).isEqualTo(expectedTime)
        }
    }
}