package com.skillhelper.api.mappers

import com.skillhelper.application.entities.Username
import com.skillhelper.api.models.RequestDto

fun Username.toRequestDto(imageSrc: String?): RequestDto =
    RequestDto(
        username = this.value,
        profileImage = imageSrc
    )