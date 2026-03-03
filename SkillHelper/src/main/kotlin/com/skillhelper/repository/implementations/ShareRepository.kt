package com.skillhelper.repository.implementations

import com.skillhelper.domain.entities.Share
import com.skillhelper.domain.entities.ShareId
import com.skillhelper.domain.entities.Username
import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.mappers.toDomain
import com.skillhelper.repository.models.ShareDbo
import com.skillhelper.repository.models.UserDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class ShareRepository(jdbc: JdbcClient): IShareRepository, BaseRepository(jdbc, "[Share]") {
    override fun deleteShare(shareId: ShareId) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE Id = :shareId;
        """.trimIndent();

        val params = mapOf(
            "shareId" to shareId.value
        );

        execute(sql, params);
    }

    override fun deleteAllForUser(username: Username) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE ForUser = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value
        );

        execute(sql, params);
    }

    override fun addShare(share: Share): ShareId {
        val sql = """
        INSERT INTO dbo.$tableName (
            ForUser,
            FromUser,
            Skill,
            DateShared,
            [Read]
        )
        OUTPUT INSERTED.Id
        VALUES (
            :forUser,
            :fromUser,
            :skill,
            :dateShared,
            :read
        );
        """.trimIndent()

        val params = mapOf(
            "forUser" to share.forUser.value,
            "fromUser" to share.fromUser.value,
            "skill" to share.skill.value,
            "dateShared" to share.dateShared,
            "read" to if (share.isRead()) 1 else 0
        )

        return ShareId(insert(sql, params));
    }

    override fun readShare(shareId: ShareId) {
        val sql = """
        UPDATE dbo.$tableName
        SET [Read] = 1
        WHERE Id = :shareId;
        """.trimIndent();

        val params = mapOf(
            "shareId" to shareId.value,
        );

        execute(sql, params);
    }

    override fun getAllForUser(username: Username): List<Share> {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE ForUser = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        return query<ShareDbo>(sql, params).map { it.toDomain() };
    }
}