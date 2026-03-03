package com.skillhelper.feature.mappers

import com.skillhelper.domain.entities.Share
import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.models.ShareCreationDto
import com.skillhelper.feature.models.ShareDto

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