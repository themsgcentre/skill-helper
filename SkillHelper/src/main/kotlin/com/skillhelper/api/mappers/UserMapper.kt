package com.skillhelper.api.mappers

import com.skillhelper.domain.entities.Profile
import com.skillhelper.domain.entities.User
import com.skillhelper.domain.entities.Username
import com.skillhelper.application.models.ProfileDto
import com.skillhelper.application.models.UserDto

fun UserDto.toDomain(encodedPassword: String): User =
    User(
        username = Username(this.username),
        password = encodedPassword,
        profile = Profile(
            bio = this.bio,
            profileImage = this.profileImage
        )
    )

fun User.toProfileDto(): ProfileDto =
    ProfileDto(
        username = this.username.value,
        bio = this.profile.bio,
        profileImage = this.profile.profileImage
    )