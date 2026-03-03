package com.skillhelper.repository.helpers

import com.skillhelper.domain.entities.Skill
import com.skillhelper.domain.entities.SkillId
import org.springframework.jdbc.core.simple.JdbcClient

fun insertSkill(
    jdbc: JdbcClient,
    skill: Skill
): SkillId {
    val ret = jdbc.sql(
        """
            INSERT INTO dbo.[Skill] ([Name], [Description], [StressLevel], [Author], [Visibility], [ImageSrc])
            OUTPUT INSERTED.Id
            VALUES (:n, :d, :s, :a, :v, :i);
            """.trimIndent()
    )
        .param("n", skill.name)
        .param("d", skill.description)
        .param("s", skill.stressLevel.value)
        .param("a", skill.author?.value)
        .param("v", skill.visibility.id)
        .param("i", skill.imageSrc)
        .query(Long::class.java)
        .single()
    return SkillId(ret)
}