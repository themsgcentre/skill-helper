package com.skillhelper.api.mappers

import com.skillhelper.domain.entities.Skill
import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.StressLevel
import com.skillhelper.domain.entities.Username
import com.skillhelper.domain.entities.Visibility
import com.skillhelper.application.models.SkillDto

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