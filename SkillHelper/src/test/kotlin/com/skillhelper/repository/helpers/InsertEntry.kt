package com.skillhelper.repository.helpers

import com.skillhelper.repository.models.EntryDbo
import org.springframework.jdbc.core.simple.JdbcClient

fun insertEntry(jdbc: JdbcClient, entry: EntryDbo): Long {
    return jdbc.sql(
        """
            INSERT INTO dbo.[Entry] ([Username], [Text], [StressLevel], [Time])
            OUTPUT INSERTED.Id
            VALUES (:u, :t, :s, :time);
            """.trimIndent()
    )
        .param("u", entry.username)
        .param("t", entry.text)
        .param("s", entry.stressLevel)
        .param("time", entry.time)
        .query(Long::class.java)
        .single()
}