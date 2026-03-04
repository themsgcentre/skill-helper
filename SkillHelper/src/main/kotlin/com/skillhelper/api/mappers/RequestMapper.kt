package com.skillhelper.api.mappers

import com.skillhelper.api.models.RequestDto
import com.skillhelper.application.entities.Request

fun Request.toDto(): RequestDto = RequestDto(
    username = this.username.value,
    profileImage = this.image,
)