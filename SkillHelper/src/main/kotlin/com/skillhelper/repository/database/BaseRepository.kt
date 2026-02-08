package com.skillhelper.repository.database

import org.springframework.jdbc.core.simple.JdbcClient

abstract class BaseRepository(
    protected val jdbc: JdbcClient,
    protected val tableName: String
) {
    protected final inline fun <reified T : Any> query(
        sql: String,
        params: Map<String, Any?> = emptyMap()
    ): List<T> =
        jdbc.sql(sql)
            .params(params)
            .query(T::class.java)
            .list()

    protected final fun execute(
        sql: String,
        params: Map<String, Any?> = emptyMap()
    ): Int =
        jdbc.sql(sql)
            .params(params)
            .update()

    protected final fun insert(
        sql: String,
        params: Map<String, Any?> = emptyMap()
    ): Long =
        jdbc.sql(sql)
            .params(params)
            .query(Long::class.java)
            .single()
}
