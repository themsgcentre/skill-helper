package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.models.UserDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class FriendRepository(jdbc: JdbcClient): IFriendRepository, BaseRepository(jdbc, "[Friend]") {
    override fun addFriend(username: String, friend: String) {
        val sql = """
        INSERT INTO dbo.$tableName(
            User,
            Friend
        )
        VALUES (
            :username,
            :friend
        );
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "friend" to friend
        );

        execute(sql, params);
    }

    override fun removeFriend(username: String, friend: String) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE User = :username and Friend = :friend;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "friend" to friend
        );

        execute(sql, params);
    }

    override fun getFriends(username: String): List<String> {
        val sql = """
        SELECT (Friend) from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        return query<String>(sql, params);
    }

}