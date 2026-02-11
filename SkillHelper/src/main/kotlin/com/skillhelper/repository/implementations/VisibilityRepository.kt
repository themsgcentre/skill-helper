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
        SELECT * from dbo.$tableName;
        """.trimIndent();

        return query<VisibilityDbo>(sql);
    }
}