package com.skillhelper.repository.helpers

import com.skillhelper.repository.models.SkillDbo
import org.springframework.jdbc.core.simple.JdbcClient

fun insertSkill(
    jdbc: JdbcClient,
    skill: SkillDbo
): Long {
    return jdbc.sql(
        """
            INSERT INTO dbo.[Skill] ([Name], [Description], [StressLevel], [Author], [Visibility], [ImageSrc])
            OUTPUT INSERTED.Id
            VALUES (:n, :d, :s, :a, :v, :i);
            """.trimIndent()
    )
        .param("n", skill.name)
        .param("d", skill.description)
        .param("s", skill.stressLevel)
        .param("a", skill.author)
        .param("v", skill.visibility)
        .param("i", skill.imageSrc)
        .query(Long::class.java)
        .single()
}