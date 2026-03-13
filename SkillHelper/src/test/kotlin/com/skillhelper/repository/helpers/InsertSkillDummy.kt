package com.skillhelper.repository.helpers

import org.springframework.jdbc.core.simple.JdbcClient

fun insertSkillDummy(jdbc: JdbcClient, name: String, author: String): Long {
    return jdbc.sql(
        """
        INSERT INTO dbo.[Skill] (Name, Description, StressLevel, Author, Visibility, ImageSrc)
        OUTPUT INSERTED.Id
        VALUES (:n, 'desc', 1, :author, 1, NULL);
        """.trimIndent()
    )
        .param("n", name)
        .param("author", author)
        .query(Long::class.java)
        .single()
}