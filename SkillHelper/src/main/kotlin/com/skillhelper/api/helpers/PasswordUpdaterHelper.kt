package com.skillhelper.api.helpers

data class PasswordUpdaterHelper(
    val username: String,
    val oldPassword: String,
    val newPassword: String,
)