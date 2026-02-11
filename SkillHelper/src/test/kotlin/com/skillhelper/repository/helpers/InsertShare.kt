package com.skillhelper.repository.helpers

import com.skillhelper.repository.models.ShareDbo
import org.springframework.jdbc.core.simple.JdbcClient

fun insertShare(jdbc: JdbcClient, share: ShareDbo): Long {
    return jdbc.sql(
        """
        INSERT INTO dbo.[Share] (ForUser, FromUser, Skill, DateShared, [Read])
        OUTPUT INSERTED.Id
        VALUES (:forUser, :fromUser, :skill, :dateShared, :read);
        """.trimIndent()
    )
        .param("forUser", share.forUser)
        .param("fromUser", share.fromUser)
        .param("skill", share.skill)
        .param("dateShared", share.dateShared)
        .param("read", if (share.read) 1 else 0)
        .query(Long::class.java)
        .single()
}