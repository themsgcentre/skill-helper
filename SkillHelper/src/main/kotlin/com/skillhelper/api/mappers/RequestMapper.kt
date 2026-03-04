package com.skillhelper.api.mappers

import com.skillhelper.domain.entities.Username
import com.skillhelper.application.models.RequestDto

fun Username.toRequestDto(imageSrc: String?): RequestDto =
    RequestDto(
        username = this.value,
        profileImage = imageSrc
    )