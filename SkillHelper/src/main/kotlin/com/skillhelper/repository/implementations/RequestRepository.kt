package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class RequestRepository(jdbc: JdbcClient): IRequestRepository, BaseRepository(jdbc, "[Request]") {
    override fun addRequest(username: String, request: String) {
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
            "username" to username,
            "request" to request
        );

        execute(sql, params);
    }

    override fun removeRequest(username: String, request: String) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE [User] = :username AND Request = :request;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "request" to request
        );

        execute(sql, params);
    }

    override fun getRequests(username: String): List<String> {
        val sql = """
        SELECT (Request) from dbo.$tableName
        WHERE [User] = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        return query<String>(sql, params);
    }
}