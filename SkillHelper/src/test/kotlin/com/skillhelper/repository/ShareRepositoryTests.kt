package com.skillhelper.repository

import com.skillhelper.repository.helpers.insertShare
import com.skillhelper.repository.helpers.insertSkillDummy
import com.skillhelper.repository.helpers.insertUser
import com.skillhelper.repository.implementations.ShareRepository
import com.skillhelper.repository.models.ShareDbo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
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

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

}