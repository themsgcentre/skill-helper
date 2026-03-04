package com.skillhelper.repository

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.repository.helpers.insertSkill
import com.skillhelper.repository.helpers.insertUser
import com.skillhelper.repository.implementations.SkillRepository
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
class SkillRepositoryTests {
    @Autowired
    lateinit var repository: SkillRepository

    @Autowired
    lateinit var jdbc: JdbcClient

    @BeforeEach
    fun setUp() {
        jdbc.sql("""DELETE FROM dbo.[Skill];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @AfterAll
    fun tearDown() {
        jdbc.sql("""DELETE FROM dbo.[Skill];""").update()
        jdbc.sql("""DELETE FROM dbo.[User];""").update()
    }

    @Test
    fun getAllSkills_NoSkills_ReturnsEmptyList() {
        val actual = repository.getAllSkills()
        assertThat(actual).isEmpty()
    }

    @Test
    fun getAllSkills_HasSkills_ReturnsCorrectList() {
        val author = "author1"
        insertUser(jdbc, author)
        val skill1 = Skill(
            name = "skill 1",
            description = "desc 1",
            stressLevel = StressLevel(1),
            author = Username(author),
            visibility = Visibility.PUBLIC,
            imageSrc = "src1"
        )

        val skill2 = Skill(
            name = "skill 2",
            description = "desc 2",
            stressLevel = StressLevel(3),
            author = null,
            visibility = Visibility.PUBLIC,
            imageSrc = null
        )

        val id1 = insertSkill(
            jdbc,
            skill1
        )

        val id2 = insertSkill(
            jdbc,
            skill2
        )

        val expected = listOf(
            skill1.copy(id = id1),
            skill2.copy(id = id2)
        )

        val actual = repository.getAllSkills()

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun getSkillById_IdExists_ReturnsSkill() {
        val author = "author1"
        insertUser(jdbc, author)

        val skill = Skill(
            name = "skill 1",
            description = "desc 1",
            stressLevel = StressLevel(2),
            author = Username(author),
            visibility = Visibility.PUBLIC,
            imageSrc = "img1"
        )

        val insertedId = insertSkill(jdbc, skill)

        val actual = repository.getSkillById(insertedId)

        val expected = skill.copy(id = insertedId)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getSkillById_IdDoesNotExist_ReturnsNull() {
        val actual = repository.getSkillById(SkillId(9999L))

        assertThat(actual).isNull()
    }

    @Test
    fun getSkillsBySearch_PartialMatch_ReturnsMatchingSkills() {
        val author = Username("author1")
        insertUser(jdbc, author.value)

        val s1 = Skill(
            id = null,
            name = "first",
            description = "a description",
            stressLevel = StressLevel(1),
            author = author,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val s2 = Skill(
            id = null,
            name = "second",
            description = "similar to first",
            stressLevel = StressLevel(2),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val s3 = Skill(
            id = null,
            name = "third",
            description = "nothing to see here",
            stressLevel = StressLevel(3),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val id1 = insertSkill(jdbc, s1)
        val id2 = insertSkill(jdbc, s2)
        insertSkill(jdbc, s3)

        val actual = repository.getSkillsBySearch("fi")

        val expected = listOf(
            s1.copy(id = id1),
            s2.copy(id = id2)
        )

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun getSkillsBySearch_NoMatch_ReturnsEmptyList() {
        val s1 = Skill(
            id = null,
            name = "test",
            description = "test",
            stressLevel = StressLevel(1),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        insertSkill(jdbc, s1)

        val actual = repository.getSkillsBySearch("no result")

        assertThat(actual).isEmpty()
    }

    @Test
    fun getSkillsByStressLevel_InRange_ReturnsMatchingSkills() {
        val s1 = Skill(
            id = null,
            name = "first",
            description = "desc",
            stressLevel = StressLevel(0),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val s2 = Skill(
            id = null,
            name = "second",
            description = "desc",
            stressLevel = StressLevel(30),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val s3 = Skill(
            id = null,
            name = "third",
            description = "desc",
            stressLevel = StressLevel(50),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val id1 = insertSkill(jdbc, s1)
        val id2 = insertSkill(jdbc, s2)
        insertSkill(jdbc, s3)

        val actual = repository.getSkillsByStressLevel(
            StressLevel(0),
            StressLevel(35)
        )

        val expected = listOf(
            s1.copy(id = id1),
            s2.copy(id = id2)
        )

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun getSkillsByStressLevel_NoMatch_ReturnsEmptyList() {
        val s1 = Skill(
            id = null,
            name = "first",
            description = "desc",
            stressLevel = StressLevel(50),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        insertSkill(jdbc, s1)

        val actual = repository.getSkillsByStressLevel(
            StressLevel(10),
            StressLevel(30)
        )

        assertThat(actual).isEmpty()
    }

    @Test
    fun addSkill_AuthorNull_InsertsSkill() {
        val skill = Skill(
            id = null,
            name = "first",
            description = "desc",
            stressLevel = StressLevel(1),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = "src"
        )

        val newId = repository.addSkill(skill)

        val actual = repository.getAllSkills()

        assertThat(actual).contains(
            skill.copy(id = newId)
        )
    }

    @Test
    fun addSkill_AuthorExists_InsertsSkill() {
        val author = "author1"
        insertUser(jdbc, author)


        val skill = Skill(
            id = null,
            name = "second",
            description = "desc",
            stressLevel = StressLevel(1),
            author = Username(author),
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val newId = repository.addSkill(skill)

        val actual = repository.getAllSkills()
        assertThat(actual).contains(skill.copy(id = newId))
    }

    @Test
    fun addSkill_AuthorDoesNotExist_ThrowsForeignKeyException() {
        val skill = Skill(
            id = null,
            name = "third",
            description = "desc",
            stressLevel = StressLevel(3),
            author = Username("missingUser"),
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        assertThatThrownBy { repository.addSkill(skill) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addSkill_StressLevelBelowZero_ThrowsDataIntegrityViolation() {
        val skill = Skill(
            id = null,
            name = "first",
            description = "desc",
            stressLevel = StressLevel(-1),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        assertThatThrownBy { repository.addSkill(skill) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addSkill_StressLevelAboveHundred_ThrowsDataIntegrityViolation() {
        val skill = Skill(
            id = null,
            name = "second",
            description = "desc",
            stressLevel = StressLevel(101),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        assertThatThrownBy { repository.addSkill(skill) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateSkill_ValidData_UpdatesSkill() {
        val author = "author1"
        insertUser(jdbc, author)

        val original = Skill(null, "first", "desc", StressLevel(20), null, Username(author), visibility = Visibility.PRIVATE)
        val id = insertSkill(jdbc, original)

        val updated = Skill(
            id = id,
            name = "updated",
            description = "new desc",
            stressLevel = StressLevel(50),
            author = Username(author),
            visibility = Visibility.PRIVATE,
            imageSrc = "img"
        )

        repository.updateSkill(updated)

        val actual = repository.getSkillById(id)

        assertThat(actual).isEqualTo(updated)
    }

    @Test
    fun updateSkill_AuthorDoesNotExist_ThrowsForeignKeyException() {
        val original = Skill(null, "first", "desc", StressLevel(1), null, null, Visibility.PRIVATE)
        val id = insertSkill(jdbc, original)

        val invalid = original.copy(
            id = id,
            author = Username("missingUser")
        )

        assertThatThrownBy { repository.updateSkill(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateSkill_StressLevelBelowZero_ThrowsException() {
        val original = Skill(null, "first", "desc", StressLevel(1), null, null, Visibility.PRIVATE)
        val id = insertSkill(jdbc, original)

        val invalid = original.copy(
            id = id,
            stressLevel = StressLevel(-1)
        )

        assertThatThrownBy { repository.updateSkill(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateSkill_StressLevelAboveHundred_ThrowsException() {
        val original = Skill(null, "first", "desc", StressLevel(1), null, null, Visibility.PRIVATE)
        val id = insertSkill(jdbc, original)

        val invalid = original.copy(
            id = id,
            stressLevel = StressLevel(101)
        )

        assertThatThrownBy { repository.updateSkill(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun deleteSkill_ExistingId_RemovesSkill() {
        val skill = Skill(
            id = null,
            name = "first",
            description = "desc",
            stressLevel = StressLevel(1),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        repository.deleteSkill(id)

        val actual = repository.getSkillById(id)

        assertThat(actual).isNull()
    }

    @Test
    fun deleteSkill_IdDoesNotExist_DoesNothing() {
        val skill = Skill(
            id = null,
            name = "first",
            description = "desc",
            stressLevel = StressLevel(1),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        repository.deleteSkill(SkillId(9999L))

        val actual = repository.getSkillById(id)

        assertThat(actual).isEqualTo(skill.copy(id = id))
    }

    @Test
    fun changeVisibility_ValidVisibility_UpdatesSkill() {
        val skill = Skill(
            id = null,
            name = "first",
            description = "desc",
            stressLevel = StressLevel(1),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        repository.changeVisibility(id, Visibility.FRIENDS_ONLY)

        val actual = repository.getSkillById(id)

        assertThat(actual!!.visibility).isEqualTo(Visibility.FRIENDS_ONLY)
    }

    @Test
    fun skillExists_SkillExists_ReturnsTrue() {
        val skill = Skill(
            id = null,
            name = "first",
            description = "desc",
            stressLevel = StressLevel(50),
            author = null,
            visibility = Visibility.PRIVATE,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        val actual = repository.skillExists(id)

        assertThat(actual).isTrue()
    }

    @Test
    fun skillExists_SkillDoesNotExist_ReturnsFalse() {
        val actual = repository.skillExists(SkillId(9999L))

        assertThat(actual).isFalse()
    }
}