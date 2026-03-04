package com.skillhelper.repository.implementations

import com.skillhelper.application.entities.Username
import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class FriendRepository(jdbc: JdbcClient): IFriendRepository, BaseRepository(jdbc, "[Friend]") {
    override fun addFriend(username: Username, friend: Username) {
        val sql = """
        INSERT INTO dbo.$tableName(
            [User],
            [Friend]
        )
        VALUES (
            :username,
            :friend
        );
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "friend" to friend.value
        );

        execute(sql, params);
    }

    override fun removeFriend(username: Username, friend: Username) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE [User] = :username AND Friend = :friend;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "friend" to friend.value
        );

        execute(sql, params);
    }

    override fun getFriends(username: Username): List<Username> {
        val sql = """
        SELECT (Friend) from dbo.$tableName
        WHERE [User] = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
        );

        return query<String>(sql, params).map {
            Username(it)
        };
    }

}