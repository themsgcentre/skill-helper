package com.skillhelper.repository.implementations

import com.skillhelper.application.entities.Username
import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class RequestRepository(jdbc: JdbcClient): IRequestRepository, BaseRepository(jdbc, "[Request]") {
    override fun addRequest(username: Username, request: Username) {
        val sql = """
        INSERT INTO dbo.$tableName(
            [User],
            [Request]
        )
        VALUES (
            :username,
            :request
        );
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "request" to request.value
        );

        execute(sql, params);
    }

    override fun removeRequest(username: Username, request: Username) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE [User] = :username AND Request = :request;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "request" to request.value
        );

        execute(sql, params);
    }

    override fun getRequests(username: Username): List<Username> {
        val sql = """
        SELECT (Request) from dbo.$tableName
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