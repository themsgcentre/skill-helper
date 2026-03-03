package com.skillhelper.feature.mappers

import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.models.RequestDto

fun Username.toRequestDto(imageSrc: String?): RequestDto =
    RequestDto(
        username = this.value,
        profileImage = imageSrc
    )