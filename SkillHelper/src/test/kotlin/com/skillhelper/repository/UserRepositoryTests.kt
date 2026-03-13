package com.skillhelper.repository

import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.repository.implementations.UserRepository
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

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTests {
    @Autowired
    lateinit var repository: UserRepository

    @Autowired
    lateinit var jdbc: JdbcClient

    @BeforeEach
    fun setUp() {
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @AfterAll
    fun tearDown() {
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @Test
    fun getUserByName_NotFound_ReturnsNull() {
        val actual = repository.getUserByName(Username("test"))
        assertThat(actual).isNull()
    }

    @Test
    fun getUserByName_UserFound_ReturnsCorrectUser() {
        val user = User(Username("test username"), "test password", Profile("test image", "test bio"))

        jdbc.sql("""
        INSERT INTO dbo.[User] (Username, Password, ProfileImage, Bio)
        VALUES (:u, :p, :img, :bio)
        """.trimIndent())
            .param("u", user.username)
            .param("p", user.password)
            .param("img", user.profile.profileImage)
            .param("bio", user.profile.bio)
            .update()

        val actual = repository.getUserByName(user.username)

        assertThat(actual).isEqualTo(user)
    }

    @Test
    fun addUser_UsernameAvailable_AddsUser() {
        val user = User(Username("test username"), "test password", Profile("test image", "test bio"))

        repository.createUser(user)

        val actual = repository.getUserByName(user.username)

        assertThat(actual).isEqualTo(user)
    }

    @Test
    fun addUser_UsernameTaken_ThrowsSqlException() {
        val user = User(Username("test username"), "test password", Profile("test image", "test bio"))
        repository.createUser(user)

        assertThatThrownBy {
            repository.createUser(user)
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun getPassword_UserExists_ReturnsPassword() {
        val user = User(Username("test username"), "test password", Profile("test image", "test bio"))
        repository.createUser(user)

        val actual = repository.getPassword(user.username)

        assertThat(actual).isEqualTo("test password")
    }

    @Test
    fun getPassword_UserDoesNotExist_ReturnsNull() {
        val actual = repository.getPassword(Username("does-not-exist"))

        assertThat(actual).isNull()
    }

    @Test
    fun updatePassword_UserExists_UpdatesPassword() {
        val user = User(Username("test username"), "test password", Profile("test image", "test bio"))
        repository.createUser(user)

        repository.updatePassword(user.username, "new pw")

        val actual = repository.getPassword(user.username)
        assertThat(actual).isEqualTo("new pw")
    }

    @Test
    fun userExists_UserExists_ReturnsTrue() {
        val user = User(Username("test username"), "test password", Profile("test image", "test bio"))
        repository.createUser(user)

        val actual = repository.userExists(user.username)

        assertThat(actual).isTrue()
    }

    @Test
    fun userExists_UserDoesNotExist_ReturnsFalse() {
        val actual = repository.userExists(Username("does-not-exist"))

        assertThat(actual).isFalse()
    }

    @Test
    fun deleteUser_UserExists_DeletesUser() {
        val user = User(Username("test username"), "test password", Profile("test image", "test bio"))
        repository.createUser(user)

        repository.deleteUser(user.username)

        val actual = repository.getUserByName(user.username)
        assertThat(actual).isNull()
    }

    @Test
    fun updateBio_UserExists_UpdatesBio() {
        val user = User(Username("test username"), "test password", Profile("test image", "old bio"))
        repository.createUser(user)

        repository.updateBio(user.username, "new bio")

        val actual = repository.getUserByName(user.username)
        assertThat(actual).isNotNull
        assertThat(actual!!.profile.bio).isEqualTo("new bio")
    }

    @Test
    fun updateProfilePicture_UserExists_UpdatesProfileImage() {
        val user = User(Username("test username"), "test password", Profile("old img", "test bio"))
        repository.createUser(user)

        repository.updateProfilePicture(user.username, "new img")

        val actual = repository.getUserByName(user.username)
        assertThat(actual).isNotNull
        assertThat(actual!!.profile.profileImage).isEqualTo("new img")
    }

    @Test
    fun updateUsername_UserExists_UpdatesUsername() {
        val user = User(Username("old-name"), "test password", Profile("test image", "test bio"))
        repository.createUser(user)

        repository.updateUsername(Username("old-name"), Username("new-name"))

        val oldUser = repository.getUserByName(Username("old-name"))
        val newUser = repository.getUserByName(Username("new-name"))

        assertThat(oldUser).isNull()
        assertThat(newUser).isNotNull
        assertThat(newUser!!.username).isEqualTo("new-name")
    }
}