package com.skillhelper.api.helpers

import com.skillhelper.application.entities.Username
import org.springframework.security.core.Authentication

fun Authentication?.toUsernameOrNull(): Username? {
    return this?.name?.let { Username(it) }
}

fun Authentication.toUsername(): Username {
    return Username(this.name)
}