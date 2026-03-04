package com.skillhelper.repository.mappers

import com.skillhelper.application.entities.Share
import com.skillhelper.application.entities.ShareId
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.Username
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