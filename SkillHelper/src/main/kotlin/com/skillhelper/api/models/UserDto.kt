package com.skillhelper.api.models

data class UserDto(
    val username: String,
    val password: String,
    val profileImage: String,
    val bio: String
)