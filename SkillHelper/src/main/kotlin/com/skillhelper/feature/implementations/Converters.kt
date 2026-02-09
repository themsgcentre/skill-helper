package com.skillhelper.feature.implementations

import com.skillhelper.feature.models.FriendDto
import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.RequestDto
import com.skillhelper.feature.models.SkillDto
import com.skillhelper.feature.models.UserDto
import com.skillhelper.repository.models.SkillDbo
import com.skillhelper.repository.models.UserDbo

fun SkillDbo.toDto(): SkillDto = SkillDto(
    id = this.id,
    name = this.name,
    description = this.description,
    stressLevel = this.stressLevel,
    author = this.author,
    visibility = this.visibility,
    imageSrc = this.imageSrc
)

fun SkillDto.toDbo(): SkillDbo = SkillDbo(
    id = this.id,
    name = this.name,
    description = this.description,
    stressLevel = this.stressLevel,
    author = this.author,
    visibility = this.visibility,
    imageSrc = this.imageSrc
)

fun UserDto.toDbo(encodedPassword: String): UserDbo = UserDbo(
    username = this.username,
    password = encodedPassword,
    bio = this.bio,
    profileImage = this.profileImage,
)

fun UserDbo.toProfileDto(): ProfileDto = ProfileDto(
    username = this.username,
    bio = this.bio,
    profileImage = this.profileImage,
)

fun String.toRequestDto(imageSrc: String?): RequestDto = RequestDto(
    username = this,
    profileImage = imageSrc,
)

fun String.toFriendDto(imageSrc: String?): FriendDto = FriendDto(
    username = this,
    profileImage = imageSrc,
)