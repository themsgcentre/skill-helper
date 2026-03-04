package com.skillhelper.repository.mappers

import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.repository.models.UserDbo

fun UserDbo.toDomain(): User =
    User(
        username = Username(this.username),
        password = this.password,
        profile = Profile(
            bio = this.bio,
            profileImage = this.profileImage
        )
    )