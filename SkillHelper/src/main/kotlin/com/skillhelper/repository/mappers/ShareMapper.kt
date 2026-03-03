package com.skillhelper.repository.mappers

import com.skillhelper.domain.entities.Share
import com.skillhelper.domain.entities.ShareId
import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.Username
import com.skillhelper.repository.models.ShareDbo

fun ShareDbo.toDomain(): Share =
    Share(
        id = ShareId(this.id),
        forUser = Username(this.forUser),
        fromUser = Username(this.fromUser),
        skill = SkillId(this.skill),
        dateShared = this.dateShared,
        read = this.read
    )