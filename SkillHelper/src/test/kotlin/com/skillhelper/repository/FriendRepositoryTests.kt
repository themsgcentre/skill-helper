package com.skillhelper.repository

import com.skillhelper.repository.helpers.insertUser
import com.skillhelper.repository.implementations.FriendRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendRepositoryTests {
    @Autowired
    lateinit var repository: FriendRepository

    @Autowired
    lateinit var jdbc: JdbcClient

    @BeforeEach
    fun setUp() {
        jdbc.sql("""DELETE FROM dbo.[Friend];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @Test
    fun getFriends_UserDoesNotExist_ReturnsEmptyList() {
        val actual = repository.getFriends("does-not-exist")
        assertThat(actual).isEmpty()
    }

    @Test
    fun getFriends_UserHasFriends_ReturnsFriendUsernames() {
        val username = "username"
        val friends = listOf("friend1", "friend2", "friend3")

        insertUser(jdbc, username)
        friends.forEach { insertUser(jdbc, it) }

        friends.forEach { f ->
            jdbc.sql(
                """
                INSERT INTO dbo.[Friend] ([User], [Friend])
                VALUES (:u, :f);
                """.trimIndent()
            )
                .param("u", username)
                .param("f", f)
                .update()
        }

        val actual = repository.getFriends(username)

        assertThat(actual).containsExactlyInAnyOrderElementsOf(friends)
    }

    @Test
    fun addFriend_ValidUsers_AddsData() {
        val username = "user1"
        val friend = "user2"

        insertUser(jdbc, username)
        insertUser(jdbc,friend)

        repository.addFriend(username, friend)

        val actual = repository.getFriends(username)
        assertThat(actual).containsExactly(friend)
    }

    @Test
    fun addFriend_DuplicateFriendship_ThrowsException() {
        val username = "user1"
        val friend = "user2"

        insertUser(jdbc, username)
        insertUser(jdbc, friend)

        repository.addFriend(username, friend)

        assertThatThrownBy { repository.addFriend(username, friend) }
            .isInstanceOf(DuplicateKeyException::class.java)
    }

    @Test
    fun addFriend_UserDoesNotExist_ThrowsForeignKeyException() {
        val username = "missingUser"
        val friend = "existingFriend"

        insertUser(jdbc, friend)

        assertThatThrownBy {
            repository.addFriend(username, friend)
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addFriend_FriendDoesNotExist_ThrowsForeignKeyException() {
        val username = "existingUser"
        val friend = "missingFriend"

        insertUser(jdbc, username)

        assertThatThrownBy {
            repository.addFriend(username, friend)
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun removeFriend_ExistingFriendship_RemovesData() {
        val username = "user1"
        val friend = "user2"

        insertUser(jdbc, username)
        insertUser(jdbc, friend)

        repository.addFriend(username, friend)

        repository.removeFriend(username, friend)

        val actual = repository.getFriends(username)
        assertThat(actual).isEmpty()
    }

    @Test
    fun removeFriend_NotExistingFriendship_DoesNotAffectFriends() {
        val username = "user1"
        val friend = "user2"

        insertUser(jdbc, username)
        insertUser(jdbc, friend)

        repository.removeFriend(username, friend)

        val actual = repository.getFriends(username)
        assertThat(actual).isEmpty()
    }

}