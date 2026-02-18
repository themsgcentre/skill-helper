package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.models.EntryDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class EntryRepository(jdbc: JdbcClient): IEntryRepository, BaseRepository(jdbc, "[Entry]") {
    override fun getEntries(username: String): List<EntryDbo> {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE [Username] = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        return query<EntryDbo>(sql, params);
    }

    override fun getEntryById(id: Long): EntryDbo? {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE [Id] = :id;
        """.trimIndent();

        val params = mapOf(
            "id" to id,
        );

        return query<EntryDbo>(sql, params).firstOrNull();
    }

    override fun addEntry(entry: EntryDbo): Long {
        val sql = """
        INSERT INTO dbo.$tableName(
            [Username],
            [Text],
            [StressLevel],
            [Time]
        )
        VALUES (
            :username,
            :text,
            :stressLevel,
            :time
        );
        """.trimIndent();

        val params = mapOf(
            "username" to entry.username,
            "text" to entry.text,
            "stressLevel" to entry.stressLevel,
            "time" to entry.time,
        );

        return insert(sql, params);
    }

    override fun updateEntry(entry: EntryDbo) {
        val sql = """
        UPDATE dbo.$tableName
        SET 
            Text = :text,
            StressLevel = :stressLevel,
            Time = :time,
            
        WHERE Id = :id;
        """.trimIndent();

        val params = mapOf(
            "id" to entry.id,
            "text" to entry.text,
            "stressLevel" to entry.stressLevel,
            "time" to entry.time,
        );

        execute(sql, params);
    }

    override fun deleteEntry(id: Long) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE [Id] = :id;
        """.trimIndent();

        val params = mapOf(
            "id" to id
        );

        execute(sql, params);
    }
}