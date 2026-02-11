package com.skillhelper.repository

import com.skillhelper.repository.implementations.RequestRepository
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
class RequestRepositoryTests {

    @Autowired
    lateinit var repository: RequestRepository

    @Autowired
    lateinit var jdbc: JdbcClient

    @BeforeEach
    fun setup() {
        jdbc.sql("""DELETE FROM dbo.[Request];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @Test
    fun getRequests_UserDoesNotExist_ReturnsEmptyList() {
        val actual = repository.getRequests("does-not-exist")
        assertThat(actual).isEmpty()
    }

    @Test
    fun getRequests_UserHasRequests_ReturnsRequestUsernames() {
        val username = "username"
        val requests = listOf("req1", "req2", "req3")

        insertUser(username)
        requests.forEach { insertUser(it) }

        requests.forEach { r ->
            jdbc.sql(
                """
                INSERT INTO dbo.[Request] ([User], [Request])
                VALUES (:u, :r);
                """.trimIndent()
            )
                .param("u", username)
                .param("r", r)
                .update()
        }

        val actual = repository.getRequests(username)

        assertThat(actual).containsExactlyInAnyOrderElementsOf(requests)
    }

    @Test
    fun addRequest_ValidUsers_AddsData() {
        val username = "user1"
        val request = "user2"

        insertUser(username)
        insertUser(request)

        repository.addRequest(username, request)

        val actual = repository.getRequests(username)
        assertThat(actual).containsExactly(request)
    }

    @Test
    fun addRequest_DuplicateRequest_ThrowsException() {
        val username = "user1"
        val request = "user2"

        insertUser(username)
        insertUser(request)

        repository.addRequest(username, request)

        assertThatThrownBy { repository.addRequest(username, request) }
            .isInstanceOf(DuplicateKeyException::class.java)
    }

    @Test
    fun addRequest_UserDoesNotExist_ThrowsForeignKeyException() {
        val username = "missingUser"
        val request = "existingReq"

        insertUser(request)

        assertThatThrownBy {
            repository.addRequest(username, request)
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addRequest_RequestUserDoesNotExist_ThrowsForeignKeyException() {
        val username = "missingUser"
        val request = "missingReq"

        insertUser(username)

        assertThatThrownBy {
            repository.addRequest(username, request)
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun removeRequest_ExistingRequest_RemovesData() {
        val username = "user1"
        val request = "user2"

        insertUser(username)
        insertUser(request)

        repository.addRequest(username, request)

        repository.removeRequest(username, request)

        val actual = repository.getRequests(username)
        assertThat(actual).isEmpty()
    }

    @Test
    fun removeRequest_NotExistingRequest_DoesNotAffectRequests() {
        val username = "user1"
        val request = "user2"

        insertUser(username)
        insertUser(request)

        repository.removeRequest(username, request)

        val actual = repository.getRequests(username)
        assertThat(actual).isEmpty()
    }

    private fun insertUser(username: String) {
        jdbc.sql(
            """
            INSERT INTO dbo.[User] (Username, Password, ProfileImage, Bio)
            VALUES (:u, :p, :img, :bio);
            """.trimIndent()
        )
            .param("u", username)
            .param("p", "pw")
            .param("img", null)
            .param("bio", "")
            .update()
    }
}
