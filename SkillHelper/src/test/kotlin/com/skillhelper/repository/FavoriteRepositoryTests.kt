package com.skillhelper.repository

import com.skillhelper.repository.helpers.insertSkillDummy
import com.skillhelper.repository.helpers.insertUser
import com.skillhelper.repository.implementations.FavoriteRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
class FavoriteRepositoryTests {
    @Autowired
    lateinit var repository: FavoriteRepository

    @Autowired
    lateinit var jdbc: JdbcClient

    @BeforeEach
    fun setUp() {
        jdbc.sql("""DELETE FROM dbo.[Favorite];""").update()
        jdbc.sql("""DELETE FROM dbo.[Skill];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @Test
    fun getFavorites_UserDoesNotExist_ReturnsEmptyList() {
        val actual = repository.getFavorites("does-not-exist")

        assertThat(actual).isEmpty()
    }

    @Test
    fun getFavorites_UserHasNoFavorites_ReturnsEmptyList() {
        insertUser(jdbc, "user1")

        val actual = repository.getFavorites("user1")

        assertThat(actual).isEmpty()
    }

    @Test
    fun getFavorites_UserHasFavorites_ReturnsSkillIds() {
        val username = "user1"
        insertUser(jdbc, username)

        val skill1 = insertSkillDummy(jdbc, "first")
        val skill2 = insertSkillDummy(jdbc, "second")

        jdbc.sql("""
            INSERT INTO dbo.[Favorite] ([User], Skill)
            VALUES (:u, :s);
        """)
            .param("u", username)
            .param("s", skill1)
            .update()

        jdbc.sql("""
            INSERT INTO dbo.[Favorite] ([User], Skill)
            VALUES (:u, :s);
        """)
            .param("u", username)
            .param("s", skill2)
            .update()

        val actual = repository.getFavorites(username)

        assertThat(actual).containsExactlyInAnyOrder(skill1, skill2)
    }

    @Test
    fun addFavorite_ValidUserAndSkill_InsertsFavorite() {
        val username = "user1"
        insertUser(jdbc, username)

        val skillId = insertSkillDummy(jdbc, "first")

        repository.addFavorite(username, skillId)

        val actual = repository.getFavorites(username)

        assertThat(actual).containsExactly(skillId)
    }

    @Test
    fun addFavorite_UserDoesNotExist_ThrowsForeignKeyException() {
        val skillId = insertSkillDummy(jdbc, "first")

        assertThatThrownBy {
            repository.addFavorite("missingUser", skillId)
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addFavorite_SkillDoesNotExist_ThrowsForeignKeyException() {
        val username = "user1"
        insertUser(jdbc, username)

        assertThatThrownBy {
            repository.addFavorite(username, 9999L)
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun removeFavorite_ExistingFavorite_RemovesIt() {
        val username = "user1"
        insertUser(jdbc, username)

        val skillId = insertSkillDummy(jdbc, "first")

        repository.addFavorite(username, skillId)

        repository.removeFavorite(username, skillId)

        val actual = repository.getFavorites(username)
        assertThat(actual).isEmpty()
    }

    @Test
    fun removeFavorite_NotExistingFavorite_DoesNothing() {
        val username = "user1"
        insertUser(jdbc, username)

        val skillId = insertSkillDummy(jdbc, "first")

        repository.addFavorite(username, skillId)

        repository.removeFavorite(username, 9999L)

        val actual = repository.getFavorites(username)
        assertThat(actual).containsExactly(skillId)
    }
}