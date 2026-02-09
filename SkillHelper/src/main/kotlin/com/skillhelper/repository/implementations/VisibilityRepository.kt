package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IVisibilityRepository
import com.skillhelper.repository.models.VisibilityDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class VisibilityRepository(jdbc: JdbcClient): IVisibilityRepository, BaseRepository(jdbc, "[Visibility]") {
    override fun getAllVisibilityLevels(): List<VisibilityDbo> {
        val sql = """
        SELECT (Id, Description) from dbo.$tableName;
        """.trimIndent();

        return query<VisibilityDbo>(sql);
    }

    override fun getVisibilityString(id: Long): String {
        val sql = """
        SELECT (Description) from dbo.$tableName
        WHERE Id = $id;
        """.trimIndent();

        val params = mapOf("id" to id);

        return query<String>(sql, params).firstOrNull() ?: "";
    }
}