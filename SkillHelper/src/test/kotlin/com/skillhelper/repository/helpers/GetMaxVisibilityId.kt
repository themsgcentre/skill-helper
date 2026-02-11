package com.skillhelper.repository.helpers

import org.springframework.jdbc.core.simple.JdbcClient

fun getMaxVisibilityId(jdbc: JdbcClient): Long {
    return jdbc.sql("SELECT MAX(Id) FROM dbo.[Visibility];")
        .query(Long::class.java)
        .list()
        .first()
}