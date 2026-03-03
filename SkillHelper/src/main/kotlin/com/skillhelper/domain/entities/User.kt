package com.skillhelper.domain.entities

@JvmInline value class Username(val value: String)

data class User(
    val username: Username,
    val password: String,
    val profile: Profile,
)