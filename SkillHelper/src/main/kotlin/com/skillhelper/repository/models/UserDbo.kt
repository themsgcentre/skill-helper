package com.skillhelper.repository.models

data class UserDbo(
    val username: String,
    val password: String,
    val profileImage: String,
    val bio: String
)