package com.skillhelper.application.entities

private val USERNAME_FORBIDDEN_CHARS = setOf('/', '*', '&', '%', '"', '\'', '=', '$')

@JvmInline
value class Username(val value: String) {

    init {
        require(value.isNotBlank()) { "Username must not be blank" }
        require(value.length <= 16) { "Username must not be longer than 16 characters" }
        require(value.none { it.isWhitespace() || it in USERNAME_FORBIDDEN_CHARS }) {
            """Username must not contain / * & % " ' = $ or whitespace"""
        }
    }

    override fun toString(): String = value
}

data class User(
    val username: Username,
    val password: String,
    val profile: Profile,
)