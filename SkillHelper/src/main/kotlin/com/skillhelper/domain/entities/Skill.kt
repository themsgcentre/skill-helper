package com.skillhelper.domain.entities

@JvmInline value class SkillId(val value: Long)

@JvmInline
value class StressLevel(val value: Int) {
    init { require(value in 0..100) }
}

data class Skill(
    val id: SkillId? = null,
    val name: String,
    val description: String,
    val stressLevel: StressLevel,
    val imageSrc: String?,
    val author: Username?,
    val visibility: Visibility
)