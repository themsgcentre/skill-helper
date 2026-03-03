package com.skillhelper.domain.entities

@JvmInline
value class Username(val value: String) {

    init {
        require(value.isNotBlank()) { "Username must not be blank" }
        require(value.length <= 16) { "Username must not be longer than 16 characters" }
    }

    override fun toString(): String = value
}

data class User(
    val username: Username,
    val password: String,
    val profile: Profile,
)