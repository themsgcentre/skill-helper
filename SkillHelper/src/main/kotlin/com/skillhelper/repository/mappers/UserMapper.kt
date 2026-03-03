package com.skillhelper.repository.mappers

import com.skillhelper.domain.entities.Profile
import com.skillhelper.domain.entities.User
import com.skillhelper.domain.entities.Username
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