package com.skillhelper.repository

import com.skillhelper.repository.helpers.insertShare
import com.skillhelper.repository.helpers.insertSkillDummy
import com.skillhelper.repository.helpers.insertUser
import com.skillhelper.repository.implementations.ShareRepository
import com.skillhelper.repository.models.ShareDbo
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
import java.time.Instant
import java.time.ZoneId
import java.util.Date

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShareRepositoryTests {
    @Autowired
    lateinit var repository: ShareRepository

    @Autowired
    lateinit var jdbc: JdbcClient

    @BeforeEach
    fun setUp() {
        jdbc.sql("""DELETE FROM dbo.[Share];""").update()
        jdbc.sql("""DELETE FROM dbo.[Skill];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @AfterAll
    fun tearDown() {
        jdbc.sql("""DELETE FROM dbo.[Share];""").update()
        jdbc.sql("""DELETE FROM dbo.[Skill];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @Test
    fun getAllForUser_UserDoesNotExist_ReturnsEmptyList() {
        val actual = repository.getAllForUser("missingUser")
        assertThat(actual).isEmpty()
    }

    @Test
    fun getAllForUser_UserHasShares_ReturnsCorrectList() {
        val forUser = "user1"
        val fromUser1 = "user2"
        val fromUser2 = "user3"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser1)
        insertUser(jdbc, fromUser2)

        val skill1 = insertSkillDummy(jdbc, "first")
        val skill2 = insertSkillDummy(jdbc, "second")

        val share1 = ShareDbo(
            id = 0L,
            fromUser = fromUser1,
            forUser = forUser,
            skill = skill1,
            dateShared = Date.from(Instant.now()),
            read = false
        )
        val share2 = ShareDbo(
            id = 0L,
            fromUser = fromUser2,
            forUser = forUser,
            skill = skill2,
            dateShared = Date.from(Instant.now()),
            read = true
        )

        val id1 = insertShare(jdbc, share1)
        val id2 = insertShare(jdbc, share2)

        val expected = listOf(
            share1.copy(id = id1),
            share2.copy(id = id2)
        )

        val actual = repository.getAllForUser(forUser)

        assertWithDate(actual, expected)
    }

    @Test
    fun addShare_ValidData_AddsShare() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val skillId = insertSkillDummy(jdbc, "first")

        val share = ShareDbo(
            id = 0L,
            forUser = forUser,
            fromUser = fromUser,
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val newId = repository.addShare(share)

        val expected = listOf(share.copy(id = newId))

        val actual = repository.getAllForUser(forUser)

        assertWithDate(actual, expected)
    }

    @Test
    fun addShare_SkillDoesNotExist_ThrowsForeignKeyException() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val share = ShareDbo(
            id = 0L,
            forUser = forUser,
            fromUser = fromUser,
            skill = 9999L,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        assertThatThrownBy { repository.addShare(share) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addShare_FromUserDoesNotExist_ThrowsForeignKeyException() {
        val forUser = "user1"
        val fromUser = "missingUser"

        insertUser(jdbc, forUser)

        val skillId = insertSkillDummy(jdbc, "first")

        val share = ShareDbo(
            id = 0L,
            forUser = forUser,
            fromUser = fromUser,
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        assertThatThrownBy { repository.addShare(share) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addShare_ForUserDoesNotExist_ThrowsForeignKeyException() {
        val forUser = "missingUser"
        val fromUser = "user1"

        insertUser(jdbc, fromUser)

        val skillId = insertSkillDummy(jdbc, "first")

        val share = ShareDbo(
            id = 0L,
            forUser = forUser,
            fromUser = fromUser,
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        assertThatThrownBy { repository.addShare(share) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun deleteShare_ExistingShare_RemovesIt() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val skillId = insertSkillDummy(jdbc, "first")

        val share = ShareDbo(
            id = 0L,
            forUser = forUser,
            fromUser = fromUser,
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val id = repository.addShare(share)

        repository.deleteShare(id)

        val actual = repository.getAllForUser(forUser)
        assertThat(actual).isEmpty()
    }

    @Test
    fun deleteShare_ShareDoesNotExist_DoesNothing() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val skillId = insertSkillDummy(jdbc, "first")

        val share = ShareDbo(
            id = 0L,
            forUser = forUser,
            fromUser = fromUser,
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val id = repository.addShare(share)

        repository.deleteShare(9999L)

        val actual = repository.getAllForUser(forUser)
        val expected = listOf(share.copy(id = id))

        assertWithDate(actual, expected)
    }

    @Test
    fun deleteAllForUser_UserHasMultipleShares_RemovesAllForThatUser() {
        val user1 = "user1"
        val user2 = "user2"
        val fromUser = "sender"

        insertUser(jdbc, user1)
        insertUser(jdbc, user2)
        insertUser(jdbc, fromUser)

        val skill1 = insertSkillDummy(jdbc, "first")
        val skill2 = insertSkillDummy(jdbc, "second")

        val share1 = ShareDbo(
            id = 0L,
            forUser = user1,
            fromUser = fromUser,
            skill = skill1,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val share2 = ShareDbo(
            id = 0L,
            forUser = user1,
            fromUser = fromUser,
            skill = skill2,
            dateShared = Date.from(Instant.now()),
            read = true
        )

        val otherShare = ShareDbo(
            id = 0L,
            forUser = user2,
            fromUser = fromUser,
            skill = skill1,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        repository.addShare(share1)
        repository.addShare(share2)
        repository.addShare(otherShare)

        repository.deleteAllForUser(user1)

        val user1Shares = repository.getAllForUser(user1)
        val user2Shares = repository.getAllForUser(user2)

        assertThat(user1Shares).isEmpty()

        assertThat(user2Shares)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateShared")
            .hasSize(1)
    }

    private fun assertWithDate(actual: List<ShareDbo>, expected: List<ShareDbo>) {
        assertThat(actual)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateShared")
            .containsExactlyInAnyOrderElementsOf(expected)

        actual.forEach { share ->
            val expectedShare = expected.first { it.id == share.id }

            val actualDate = share.dateShared.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            val expectedDate = expectedShare.dateShared.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            assertThat(actualDate).isEqualTo(expectedDate)
        }
    }

    @Test
    fun readShare_ExistingShare_SetsReadToTrue() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val skillId = insertSkillDummy(jdbc, "first")

        val share = ShareDbo(
            id = 0L,
            forUser = forUser,
            fromUser = fromUser,
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val id = repository.addShare(share)

        repository.readShare(id)

        val actual = repository.getAllForUser(forUser).first()

        assertThat(actual.read).isTrue()
    }
}