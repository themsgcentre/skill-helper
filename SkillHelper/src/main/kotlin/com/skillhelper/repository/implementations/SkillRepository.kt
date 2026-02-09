package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.models.SkillDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class SkillRepository(jdbcClient: JdbcClient): ISkillRepository, BaseRepository(jdbcClient, "[Skill]") {
    override fun getAllSkills(): List<SkillDbo> {
        val sql = """
        SELECT (Id, Name, Description, StressLevel, Author, Visibility, ImageSrc) from dbo.$tableName
        """.trimIndent();

        return query<SkillDbo>(sql);
    }

    override fun getSkillById(id: Long): SkillDbo? {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE Id = :id;
        """.trimIndent();

        val params = mapOf(
            "id" to id,
        );

        return query<SkillDbo>(sql, params).firstOrNull();
    }

    override fun getSkillsBySearch(searchString: String): List<SkillDbo> {
        val sql = """
        SELECT DISTINCT * FROM dbo.$tableName
        WHERE
            Name        LIKE :pattern
            OR Description LIKE :pattern
            OR Author      LIKE :pattern
        """.trimIndent()

        val params = mapOf(
            "pattern" to searchString
        )

        return query<SkillDbo>(sql, params)
    }

    override fun getSkillsByStressLevel(
        minLevel: Int,
        maxLevel: Int
    ): List<SkillDbo> {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE StressLevel >= :minLevel AND StressLevel <= :maxLevel;
        """.trimIndent();

        val params = mapOf(
            "minLevel" to minLevel,
            "maxLevel" to maxLevel,
        );

        return query<SkillDbo>(sql, params);
    }

    override fun addSkill(skill: SkillDbo): Long {
        val sql = """
        INSERT INTO dbo.$tableName (
            Name,
            Description,
            StressLevel,
            Author,
            Visibility,
            ImageSrc
        )
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
            "stressLevel" to skill.stressLevel,
            "author" to skill.author,
            "visibility" to skill.visibility,
            "imageSrc" to skill.imageSrc
        )

        return insert(sql, params);
    }

    override fun updateSkill(skill: SkillDbo) {
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
            "skillId" to skill.id,
            "name" to skill.name,
            "description" to skill.description,
            "stressLevel" to skill.stressLevel,
            "author" to skill.author,
            "visibility" to skill.visibility,
            "imageSrc" to skill.imageSrc
        );

        execute(sql, params);
    }

    override fun deleteSkill(skillId: Long) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE Id = :skillId;
        """.trimIndent();

        val params = mapOf(
            "skillId" to skillId
        );

        execute(sql, params);
    }

    override fun changeVisibility(skillId: Long, visibilityId: Long) {
        val sql = """
        UPDATE dbo.$tableName
        SET Visibility =:visibility,
        WHERE Id = :skillId;
        """.trimIndent();

        val params = mapOf(
            "skillId" to skillId,
            "visibility" to visibilityId,
        );

        execute(sql, params);
    }

    override fun skillExists(skillId: Long): Boolean {
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
            "skillId" to skillId
        )

        return query<Int>(sql, params).first() == 1;
    }
}