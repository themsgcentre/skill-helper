package com.skillhelper.api.mappers

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.api.models.SkillDto

fun Skill.toDto(): SkillDto =
    SkillDto(
        id = this.id?.value!!,
        name = this.name,
        description = this.description,
        stressLevel = this.stressLevel.value,
        author = this.author?.value,
        visibility = this.visibility.id,
        imageSrc = this.imageSrc
    )

fun SkillDto.toDomain(): Skill =
    Skill(
        id = SkillId(this.id),
        name = this.name,
        description = this.description,
        stressLevel = StressLevel(this.stressLevel),
        author = this.author?.let { Username(it) },
        visibility = Visibility.fromId(this.visibility),
        imageSrc = this.imageSrc
    )