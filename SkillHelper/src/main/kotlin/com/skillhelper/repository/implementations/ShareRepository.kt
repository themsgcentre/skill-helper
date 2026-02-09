package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.models.ShareDbo
import com.skillhelper.repository.models.UserDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class ShareRepository(jdbc: JdbcClient): IShareRepository, BaseRepository(jdbc, "[Share]") {
    override fun deleteShare(shareId: Long) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE Id = :shareId;
        """.trimIndent();

        val params = mapOf(
            "shareId" to shareId
        );

        execute(sql, params);
    }

    override fun deleteAllForUser(username: String) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE ForUser = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username
        );

        execute(sql, params);
    }

    override fun addShare(shareDbo: ShareDbo) {
        val sql = """
        INSERT INTO dbo.$tableName (
            ForUser,
            FromUser,
            Skill,
            DateShared,
            Read
        )
        VALUES (
            :forUser,
            :fromUser,
            :skill,
            :dateShared,
            :read
        );
        """.trimIndent()

        val params = mapOf(
            "forUser" to shareDbo.forUser,
            "fromUser" to shareDbo.fromUser,
            "skill" to shareDbo.skill,
            "dateShared" to shareDbo.dateShared,
            "read" to shareDbo.read
        )

        insert(sql, params);
    }

    override fun readShare(shareId: Long) {
        val sql = """
        UPDATE dbo.$tableName
        SET Read = 1
        WHERE Id = :shareId;
        """.trimIndent();

        val params = mapOf(
            "shareId" to shareId,
        );

        execute(sql, params);
    }

    override fun getAllForUser(username: String): List<ShareDbo> {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        return query<ShareDbo>(sql, params);
    }
}