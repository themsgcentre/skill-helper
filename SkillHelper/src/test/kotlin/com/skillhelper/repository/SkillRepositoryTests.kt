package com.skillhelper.repository

import com.skillhelper.repository.helpers.getMaxVisibilityId
import com.skillhelper.repository.helpers.insertSkill
import com.skillhelper.repository.helpers.insertUser
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.models.SkillDbo
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

    @Test
    fun getAllSkills_NoSkills_ReturnsEmptyList() {
        val actual = repository.getAllSkills()
        assertThat(actual).isEmpty()
    }

    @Test
    fun getAllSkills_HasSkills_ReturnsCorrectList() {
        val author = "author1"
        insertUser(jdbc, author)
        val skill1 = SkillDbo(
            id = 0,
            name = "skill 1",
            description = "desc 1",
            stressLevel = 1,
            author = author,
            visibility = 1,
            imageSrc = "src1"
        )

        val skill2 = SkillDbo(
            id = 0,
            name = "skill 2",
            description = "desc 2",
            stressLevel = 3,
            author = null,
            visibility = 1,
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

        val skill = SkillDbo(
            id = 0,
            name = "skill 1",
            description = "desc 1",
            stressLevel = 2,
            author = author,
            visibility = 1,
            imageSrc = "img1"
        )

        val insertedId = insertSkill(jdbc, skill)

        val actual = repository.getSkillById(insertedId)

        val expected = skill.copy(id = insertedId)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getSkillById_IdDoesNotExist_ReturnsNull() {
        val actual = repository.getSkillById(9999L)

        assertThat(actual).isNull()
    }

    @Test
    fun getSkillsBySearch_PartialMatch_ReturnsMatchingSkills() {
        val author = "author1"
        insertUser(jdbc, author)

        val s1 = SkillDbo(0, "first", "a description", 1, author, 1, null)
        val s2 = SkillDbo(0, "second", "similar to first", 2, null, 1, null)
        val s3 = SkillDbo(0, "third", "nothing to see here", 3, null, 1, null)

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
        val s1 = SkillDbo(0, "test", "test", 1, null, 1, null)
        insertSkill(jdbc, s1)

        val actual = repository.getSkillsBySearch("no result")

        assertThat(actual).isEmpty()
    }

    @Test
    fun getSkillsByStressLevel_InRange_ReturnsMatchingSkills() {
        val s1 = SkillDbo(0, "first", "desc", 0, null, 1, null)
        val s2 = SkillDbo(0, "second", "desc", 30, null, 1, null)
        val s3 = SkillDbo(0, "third", "desc", 50, null, 1, null)

        val id1 = insertSkill(jdbc, s1)
        val id2 = insertSkill(jdbc, s2)
        insertSkill(jdbc, s3)

        val actual = repository.getSkillsByStressLevel(0, 35)

        val expected = listOf(
            s1.copy(id = id1),
            s2.copy(id = id2)
        )

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun getSkillsByStressLevel_NoMatch_ReturnsEmptyList() {
        val s1 = SkillDbo(0, "first", "desc", 50, null, 1, null)
        insertSkill(jdbc, s1)

        val actual = repository.getSkillsByStressLevel(10, 30)

        assertThat(actual).isEmpty()
    }

    @Test
    fun addSkill_AuthorNull_InsertsSkill() {
        val skill = SkillDbo(
            id = 0,
            name = "first",
            description = "desc",
            stressLevel = 1,
            author = null,
            visibility = 1,
            imageSrc = "src"
        )

        val newId = repository.addSkill(skill)

        val actual = repository.getAllSkills()
        assertThat(actual).contains(skill.copy(id = newId))
    }

    @Test
    fun addSkill_AuthorExists_InsertsSkill() {
        val author = "author1"
        insertUser(jdbc, author)


        val skill = SkillDbo(
            id = 0,
            name = "second",
            description = "desc",
            stressLevel = 2,
            author = author,
            visibility = 1,
            imageSrc = null
        )

        val newId = repository.addSkill(skill)

        val actual = repository.getAllSkills()
        assertThat(actual).contains(skill.copy(id = newId))
    }

    @Test
    fun addSkill_AuthorDoesNotExist_ThrowsForeignKeyException() {
        val skill = SkillDbo(
            id = 0,
            name = "third",
            description = "desc",
            stressLevel = 3,
            author = "missingUser",
            visibility = 1,
            imageSrc = null
        )

        assertThatThrownBy { repository.addSkill(skill) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addSkill_VisibilityDoesNotExist_ThrowsForeignKeyException() {
        val author = "author1"
        insertUser(jdbc, author)

        val invalidVisibility = getMaxVisibilityId(jdbc) + 1;

        val skill = SkillDbo(
            id = 0,
            name = "fourth",
            description = "desc",
            stressLevel = 4,
            author = author,
            visibility = invalidVisibility,
            imageSrc = null
        )

        assertThatThrownBy { repository.addSkill(skill) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addSkill_StressLevelBelowZero_ThrowsDataIntegrityViolation() {
        val skill = SkillDbo(
            id = 0,
            name = "first",
            description = "desc",
            stressLevel = -1,
            author = null,
            visibility = 1,
            imageSrc = null
        )

        assertThatThrownBy { repository.addSkill(skill) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun addSkill_StressLevelAboveHundred_ThrowsDataIntegrityViolation() {
        val skill = SkillDbo(
            id = 0,
            name = "second",
            description = "desc",
            stressLevel = 101,
            author = null,
            visibility = 1,
            imageSrc = null
        )

        assertThatThrownBy { repository.addSkill(skill) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateSkill_ValidData_UpdatesSkill() {
        val author = "author1"
        insertUser(jdbc, author)

        val original = SkillDbo(0, "first", "desc", 1, null, 1, null)
        val id = insertSkill(jdbc, original)

        val updated = SkillDbo(
            id = id,
            name = "updated",
            description = "new desc",
            stressLevel = 50,
            author = author,
            visibility = 1,
            imageSrc = "img"
        )

        repository.updateSkill(updated)

        val actual = repository.getSkillById(id)

        assertThat(actual).isEqualTo(updated)
    }

    @Test
    fun updateSkill_AuthorDoesNotExist_ThrowsForeignKeyException() {
        val original = SkillDbo(0, "first", "desc", 1, null, 1, null)
        val id = insertSkill(jdbc, original)

        val invalid = original.copy(
            id = id,
            author = "missingUser"
        )

        assertThatThrownBy { repository.updateSkill(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateSkill_VisibilityDoesNotExist_ThrowsForeignKeyException() {
        val original = SkillDbo(0, "first", "desc", 1, null, 1, null)
        val id = insertSkill(jdbc, original)

        val invalid = original.copy(
            id = id,
            visibility = getMaxVisibilityId(jdbc) + 1
        )

        assertThatThrownBy { repository.updateSkill(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateSkill_StressLevelBelowZero_ThrowsException() {
        val original = SkillDbo(0, "first", "desc", 1, null, 1, null)
        val id = insertSkill(jdbc, original)

        val invalid = original.copy(
            id = id,
            stressLevel = -1
        )

        assertThatThrownBy { repository.updateSkill(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun updateSkill_StressLevelAboveHundred_ThrowsException() {
        val original = SkillDbo(0, "first", "desc", 1, null, 1, null)
        val id = insertSkill(jdbc, original)

        val invalid = original.copy(
            id = id,
            stressLevel = 101
        )

        assertThatThrownBy { repository.updateSkill(invalid) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun deleteSkill_ExistingId_RemovesSkill() {
        val skill = SkillDbo(
            id = 0,
            name = "first",
            description = "desc",
            stressLevel = 1,
            author = null,
            visibility = 1,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        repository.deleteSkill(id)

        val actual = repository.getSkillById(id)

        assertThat(actual).isNull()
    }

    @Test
    fun deleteSkill_IdDoesNotExist_DoesNothing() {
        val skill = SkillDbo(
            id = 0,
            name = "first",
            description = "desc",
            stressLevel = 1,
            author = null,
            visibility = 1,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        repository.deleteSkill(9999L)

        val actual = repository.getSkillById(id)

        assertThat(actual).isEqualTo(skill.copy(id = id))
    }

    @Test
    fun changeVisibility_ValidVisibility_UpdatesSkill() {
        val skill = SkillDbo(
            id = 0,
            name = "first",
            description = "desc",
            stressLevel = 1,
            author = null,
            visibility = 1,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        repository.changeVisibility(id, 2)

        val actual = repository.getSkillById(id)

        assertThat(actual!!.visibility).isEqualTo(2)
    }

    @Test
    fun changeVisibility_InvalidVisibility_ThrowsForeignKeyException() {
        val skill = SkillDbo(
            id = 0,
            name = "first",
            description = "desc",
            stressLevel = 1,
            author = null,
            visibility = 1,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        assertThatThrownBy {
            repository.changeVisibility(id, getMaxVisibilityId(jdbc) + 1)
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun skillExists_SkillExists_ReturnsTrue() {
        val skill = SkillDbo(
            id = 0,
            name = "first",
            description = "desc",
            stressLevel = 1,
            author = null,
            visibility = 1,
            imageSrc = null
        )

        val id = insertSkill(jdbc, skill)

        val actual = repository.skillExists(id)

        assertThat(actual).isTrue()
    }

    @Test
    fun skillExists_SkillDoesNotExist_ReturnsFalse() {
        val actual = repository.skillExists(9999L)

        assertThat(actual).isFalse()
    }
}