package com.skillhelper.api.mappers

import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.api.models.ProfileDto
import com.skillhelper.api.models.UserDto

fun UserDto.toDomain(encodedPassword: String): User =
    User(
        username = Username(this.username),
        password = encodedPassword,
        profile = Profile(
            bio = this.bio,
            profileImage = this.profileImage
        )
    )

fun Profile.toDto(): ProfileDto = ProfileDto(
    bio = this.bio,
    profileImage = this.profileImage
)