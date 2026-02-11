package com.skillhelper.repository.helpers

import org.springframework.jdbc.core.simple.JdbcClient

fun insertSkillDummy(jdbc: JdbcClient, name: String): Long {
    return jdbc.sql(
        """
        INSERT INTO dbo.[Skill] (Name, Description, StressLevel, Author, Visibility, ImageSrc)
        OUTPUT INSERTED.Id
        VALUES (:n, 'desc', 1, NULL, 1, NULL);
        """.trimIndent()
    )
        .param("n", name)
        .query(Long::class.java)
        .single()
}