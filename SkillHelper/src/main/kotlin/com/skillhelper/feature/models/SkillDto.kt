package com.skillhelper.feature.models

data class SkillDto(
    val id: Long,
    val name: String,
    val description: String,
    val stressLevel: Int,
    val author: String?,
    val visibility: Int,
    val imageSrc: String?
)