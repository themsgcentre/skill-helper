package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.mappers.toDomain
import com.skillhelper.repository.models.SkillDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Visibility

@Service
class SkillRepository(jdbcClient: JdbcClient): ISkillRepository, BaseRepository(jdbcClient, "[Skill]") {
    override fun getAllSkills(): List<Skill> {
        val sql = """
        SELECT * from dbo.$tableName
        """.trimIndent();

        return query<SkillDbo>(sql)
            .map { it.toDomain() }
    }

    override fun getSkillById(id: SkillId): Skill? {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE Id = :id;
        """.trimIndent();

        val params = mapOf(
            "id" to id.value,
        );

        return query<SkillDbo>(sql, params).firstOrNull()?.toDomain();
    }

    override fun getSkillsBySearch(searchString: String): List<Skill> {
        val sql = """
        SELECT DISTINCT * FROM dbo.$tableName
        WHERE
            Name        LIKE :pattern
            OR Description LIKE :pattern
            OR Author      LIKE :pattern
        """.trimIndent()

        val params = mapOf(
            "pattern" to "%$searchString%"
        )

        return query<SkillDbo>(sql, params)
            .map { it.toDomain() }
    }

    override fun getSkillsByStressLevel(
        minLevel: StressLevel,
        maxLevel: StressLevel,
    ): List<Skill> {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE StressLevel >= :minLevel AND StressLevel <= :maxLevel;
        """.trimIndent();

        val params = mapOf(
            "minLevel" to minLevel.value,
            "maxLevel" to maxLevel.value,
        );

        return query<SkillDbo>(sql, params)
            .map { it.toDomain() };
    }

    override fun addSkill(skill: Skill): SkillId {
        val sql = """
        INSERT INTO dbo.$tableName (
            Name,
            Description,
            StressLevel,
            Author,
            Visibility,
            ImageSrc
        )
        OUTPUT INSERTED.Id
        VALUES (
            :name,
            :description,
            :stressLevel,
            :author,
            :visibility,
            :imageSrc
        );
        """.trimIndent()

        val params = mapOf(
            "name" to skill.name,
            "description" to skill.description,
            "stressLevel" to skill.stressLevel.value,
            "author" to skill.author?.value,
            "visibility" to skill.visibility.id,
            "imageSrc" to skill.imageSrc
        )

        return SkillId(insert(sql, params));
    }

    override fun updateSkill(skill: Skill) {
        val sql = """
        UPDATE dbo.$tableName
        SET 
            Name = :name,
            Description = :description,
            StressLevel = :stressLevel,
            Author = :author,
            Visibility =:visibility,
            ImageSrc = :imageSrc
            
        WHERE Id = :skillId;
        """.trimIndent();

        val params = mapOf(
            "skillId" to skill.id?.value,
            "name" to skill.name,
            "description" to skill.description,
            "stressLevel" to skill.stressLevel.value,
            "author" to skill.author?.value,
            "visibility" to skill.visibility.id,
            "imageSrc" to skill.imageSrc
        );

        execute(sql, params);
    }

    override fun deleteSkill(skillId: SkillId) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE Id = :skillId;
        """.trimIndent();

        val params = mapOf(
            "skillId" to skillId.value
        );

        execute(sql, params);
    }

    override fun changeVisibility(skillId: SkillId, visibility: Visibility) {
        val sql = """
        UPDATE dbo.$tableName
        SET Visibility = :visibility
        WHERE Id = :skillId;
        """.trimIndent();

        val params = mapOf(
            "skillId" to skillId.value,
            "visibility" to visibility.id,
        );

        execute(sql, params);
    }

    override fun skillExists(skillId: SkillId): Boolean {
        val sql = """
        SELECT CASE
            WHEN EXISTS (
                SELECT 1
                FROM dbo.$tableName
                WHERE Id = :skillId
            )
            THEN 1 ELSE 0
        END
        """.trimIndent()

        val params = mapOf(
            "skillId" to skillId.value
        )

        return query<Int>(sql, params).first() == 1;
    }
}