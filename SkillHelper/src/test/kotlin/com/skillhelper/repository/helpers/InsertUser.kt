package com.skillhelper.repository.helpers

import org.springframework.jdbc.core.simple.JdbcClient

fun insertUser(jdbc: JdbcClient, username: String) {
    jdbc.sql(
        """
            INSERT INTO dbo.[User] (Username, Password, ProfileImage, Bio)
            VALUES (:u, :p, :img, :bio);
            """.trimIndent()
    )
        .param("u", username)
        .param("p", "pw")
        .param("img", null)
        .param("bio", "")
        .update()
}