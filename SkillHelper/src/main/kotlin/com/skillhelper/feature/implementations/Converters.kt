package com.skillhelper.feature.implementations

import com.skillhelper.feature.models.SkillDto
import com.skillhelper.repository.models.SkillDbo

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