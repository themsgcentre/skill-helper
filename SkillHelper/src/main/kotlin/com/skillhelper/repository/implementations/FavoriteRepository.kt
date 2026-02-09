package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.models.UserDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class FavoriteRepository(jdbc: JdbcClient): IFavoriteRepository, BaseRepository(jdbc, "[Favorite]") {
    override fun addFavorite(username: String, skillId: Long) {
        val sql = """
        INSERT INTO dbo.$tableName(
            User,
            Skill
        )
        VALUES (
            :username,
            :skillId
        );
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "skillId" to skillId
        );

        execute(sql, params);
    }

    override fun removeFavorite(username: String, skillId: Long) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE User = :username AND Skill = :skillId;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "skillId" to skillId
        );

        execute(sql, params);
    }

    override fun getFavorites(username: String): List<Long> {
        val sql = """
        SELECT (Skill) from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        return query<Long>(sql, params);
    }
}