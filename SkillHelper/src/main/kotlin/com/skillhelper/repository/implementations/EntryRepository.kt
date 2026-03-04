package com.skillhelper.repository.implementations

import com.skillhelper.application.entities.Username
import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.mappers.toDomain
import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.repository.models.EntryDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class EntryRepository(jdbc: JdbcClient): IEntryRepository, BaseRepository(jdbc, "[Entry]") {
    override fun getEntries(username: Username): List<Entry> {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE [Username] = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
        );

        return query<EntryDbo>(sql, params).map {
            it.toDomain()
        };
    }

    override fun getEntryById(id: EntryId): Entry? {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE [Id] = :id;
        """.trimIndent();

        val params = mapOf(
            "id" to id.value,
        );

        return query<EntryDbo>(sql, params).firstOrNull()?.toDomain();
    }

    override fun addEntry(entry: Entry): EntryId {
        val sql = """
        INSERT INTO dbo.$tableName(
            [Username],
            [Text],
            [StressLevel],
            [Time]
        )
        OUTPUT Inserted.Id
        VALUES (
            :username,
            :text,
            :stressLevel,
            :time
        );
        """.trimIndent();

        val params = mapOf(
            "username" to entry.user.value,
            "text" to entry.text,
            "stressLevel" to entry.stressLevel.value,
            "time" to entry.time,
        );

        return EntryId(insert(sql, params));
    }

    override fun updateEntry(entry: Entry) {
        val sql = """
        UPDATE dbo.$tableName
        SET 
            Text = :text,
            StressLevel = :stressLevel,
            Time = :time 
        WHERE Id = :id;
        """.trimIndent();

        val params = mapOf(
            "id" to entry.id?.value,
            "text" to entry.text,
            "stressLevel" to entry.stressLevel.value,
            "time" to entry.time,
        );

        execute(sql, params);
    }

    override fun deleteEntry(id: EntryId) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE [Id] = :id;
        """.trimIndent();

        val params = mapOf(
            "id" to id.value
        );

        execute(sql, params);
    }

    override fun entryExists(id: EntryId): Boolean {
        val sql = """
        SELECT CASE
            WHEN EXISTS (
                SELECT 1
                FROM dbo.$tableName
                WHERE Id = :entryId
            )
            THEN 1 ELSE 0
        END
        """.trimIndent()

        val params = mapOf(
            "entryId" to id.value
        )

        return query<Int>(sql, params).first() == 1;
    }
}