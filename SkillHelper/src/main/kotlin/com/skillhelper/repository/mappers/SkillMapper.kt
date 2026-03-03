package com.skillhelper.repository.mappers

import com.skillhelper.domain.entities.Skill
import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.StressLevel
import com.skillhelper.domain.entities.Username
import com.skillhelper.domain.entities.Visibility
import com.skillhelper.repository.models.SkillDbo

fun SkillDbo.toDomain(): Skill =
    Skill(
        id = SkillId(this.id),
        name = this.name,
        description = this.description,
        stressLevel = StressLevel(this.stressLevel),
        imageSrc = this.imageSrc,
        author = this.author?.let { Username(it) },
        visibility = Visibility.fromId(this.visibility)
    )