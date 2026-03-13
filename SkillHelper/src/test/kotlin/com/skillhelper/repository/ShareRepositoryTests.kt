package com.skillhelper.repository

import com.skillhelper.application.entities.Share
import com.skillhelper.application.entities.ShareId
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.Username
import com.skillhelper.repository.helpers.insertShare
import com.skillhelper.repository.helpers.insertSkillDummy
import com.skillhelper.repository.helpers.insertUser
import com.skillhelper.repository.implementations.ShareRepository
import com.skillhelper.repository.mappers.toDomain
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
        val actual = repository.getAllForUser(Username("missingUser"))
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

        val skill1 = insertSkillDummy(jdbc, "first", fromUser1)
        val skill2 = insertSkillDummy(jdbc, "second", fromUser2)

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
            share1.copy(id = id1).toDomain(),
            share2.copy(id = id2).toDomain()
        )

        val actual = repository.getAllForUser(Username(forUser))

        assertWithDate(actual, expected)
    }

    @Test
    fun addShare_ValidData_AddsShare() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val skillId = SkillId(insertSkillDummy(jdbc, "first", fromUser))

        val share = Share(
            id = null,
            forUser = Username(forUser),
            fromUser = Username(fromUser),
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val newId = repository.addShare(share)

        val expected = listOf(share.copy(id = newId))

        val actual = repository.getAllForUser(Username(forUser))

        assertWithDate(actual, expected)
    }

    @Test
    fun addShare_SkillDoesNotExist_ThrowsForeignKeyException() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val share = Share(
            id = null,
            forUser = Username(forUser),
            fromUser = Username(fromUser),
            skill = SkillId(9999L),
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

        val skillId = SkillId(insertSkillDummy(jdbc, "first", forUser))

        val share = Share(
            id = null,
            forUser = Username(forUser),
            fromUser = Username(fromUser),
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

        val skillId = SkillId(insertSkillDummy(jdbc, "first", fromUser))

        val share = Share(
            id = null,
            forUser = Username(forUser),
            fromUser = Username(fromUser),
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

        val skillId = SkillId(insertSkillDummy(jdbc, "first", fromUser))

        val share = Share(
            id = null,
            forUser = Username(forUser),
            fromUser = Username(fromUser),
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val id = repository.addShare(share)

        repository.deleteShare(id)

        val actual = repository.getAllForUser(Username(forUser))
        assertThat(actual).isEmpty()
    }

    @Test
    fun deleteShare_ShareDoesNotExist_DoesNothing() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val skillId = SkillId(insertSkillDummy(jdbc, "first", fromUser))

        val share = Share(
            id = null,
            forUser = Username(forUser),
            fromUser = Username(fromUser),
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val id = repository.addShare(share)

        repository.deleteShare(ShareId(9999L))

        val actual = repository.getAllForUser(Username(forUser))
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

        val skill1 = SkillId(insertSkillDummy(jdbc, "first", user1))
        val skill2 = SkillId(insertSkillDummy(jdbc, "second", user1))

        val share1 = Share(
            id = null,
            forUser = Username(user1),
            fromUser = Username(fromUser),
            skill = skill1,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val share2 = Share(
            id = null,
            forUser = Username(user1),
            fromUser = Username(fromUser),
            skill = skill2,
            dateShared = Date.from(Instant.now()),
            read = true
        )

        val otherShare = Share(
            id = null,
            forUser = Username(user2),
            fromUser = Username(fromUser),
            skill = skill1,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        repository.addShare(share1)
        repository.addShare(share2)
        repository.addShare(otherShare)

        repository.deleteAllForUser(Username(user1))

        val user1Shares = repository.getAllForUser(Username(user1))
        val user2Shares = repository.getAllForUser(Username(user2))

        assertThat(user1Shares).isEmpty()

        assertThat(user2Shares)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateShared")
            .hasSize(1)
    }

    @Test
    fun readShare_ExistingShare_SetsReadToTrue() {
        val forUser = "user1"
        val fromUser = "user2"

        insertUser(jdbc, forUser)
        insertUser(jdbc, fromUser)

        val skillId = SkillId(insertSkillDummy(jdbc, "first", fromUser))

        val share = Share(
            id = null,
            forUser = Username(forUser),
            fromUser = Username(fromUser),
            skill = skillId,
            dateShared = Date.from(Instant.now()),
            read = false
        )

        val id = repository.addShare(share)

        repository.readShare(id)

        val actual = repository.getAllForUser(Username(forUser)).first()

        assertThat(actual.isRead()).isTrue()
    }

    private fun assertWithDate(actual: List<Share>, expected: List<Share>) {
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
}