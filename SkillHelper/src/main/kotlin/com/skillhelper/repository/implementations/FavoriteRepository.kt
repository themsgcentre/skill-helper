package com.skillhelper.repository.implementations

import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.Username
import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class FavoriteRepository(jdbc: JdbcClient): IFavoriteRepository, BaseRepository(jdbc, "[Favorite]") {
    override fun addFavorite(username: Username, skillId: SkillId) {
        val sql = """
        INSERT INTO dbo.$tableName(
            [User],
            [Skill]
        )
        VALUES (
            :username,
            :skillId
        );
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "skillId" to skillId.value
        );

        execute(sql, params);
    }

    override fun removeFavorite(username: Username, skillId: SkillId) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE [User] = :username AND [Skill] = :skillId;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "skillId" to skillId.value
        );

        execute(sql, params);
    }

    override fun getFavorites(username: Username): List<SkillId> {
        val sql = """
        SELECT [Skill] from dbo.$tableName
        WHERE [User] = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
        );

        return query<Long>(sql, params).map {
            SkillId(it)
        };
    }
}