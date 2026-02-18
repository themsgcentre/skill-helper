package com.skillhelper.feature.implementations

import com.skillhelper.feature.models.EntryDto
import com.skillhelper.feature.models.FriendDto
import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.RequestDto
import com.skillhelper.feature.models.ShareCreationDto
import com.skillhelper.feature.models.ShareDto
import com.skillhelper.feature.models.SkillDto
import com.skillhelper.feature.models.UserDto
import com.skillhelper.feature.models.VisibilityDto
import com.skillhelper.repository.models.EntryDbo
import com.skillhelper.repository.models.ShareDbo
import com.skillhelper.repository.models.SkillDbo
import com.skillhelper.repository.models.UserDbo
import com.skillhelper.repository.models.VisibilityDbo

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

fun ShareCreationDto.toDbo(): ShareDbo = ShareDbo(
    id = 0,
    forUser = this.to,
    fromUser = this.from,
    skill = this.skillId,
    read = false,
    dateShared = this.dateShared
)

fun ShareDbo.toDto(profileImg: String?, skillImg: String?): ShareDto = ShareDto(
    id = this.id,
    from = this.fromUser,
    skillId = this.skill,
    dateShared = this.dateShared,
    fromProfileImg = profileImg,
    skillImg = skillImg,
)

fun VisibilityDbo.toDto(): VisibilityDto = VisibilityDto(
    id = this.id,
    text = this.description
)

fun EntryDbo.toDto(): EntryDto = EntryDto(
    id = this.id,
    username = this.username,
    time = this.time,
    text = this.text,
    stressLevel = this.stressLevel,
)

fun EntryDto.toDbo(): EntryDbo = EntryDbo(
    id = this.id,
    username = this.username,
    time = this.time,
    text = this.text,
    stressLevel = this.stressLevel,
)