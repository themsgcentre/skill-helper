package com.skillhelper.api.mappers

import com.skillhelper.application.entities.Share
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.Username
import com.skillhelper.api.models.ShareCreationDto
import com.skillhelper.api.models.ShareDto

fun ShareCreationDto.toDomain(): Share =
    Share(
        id = null,
        forUser = Username(this.to),
        fromUser = Username(this.from),
        skill = SkillId(this.skillId),
        dateShared = this.dateShared,
        read = false
    )

fun Share.toDto(profileImg: String?, skillImg: String?): ShareDto =
    ShareDto(
        id = this.id?.value ?: 0L,
        from = this.fromUser.value,
        skillId = this.skill.value,
        dateShared = this.dateShared,
        fromProfileImg = profileImg,
        skillImg = skillImg
    )