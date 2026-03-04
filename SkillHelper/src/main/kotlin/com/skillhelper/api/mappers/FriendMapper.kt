package com.skillhelper.api.mappers

import com.skillhelper.domain.entities.Username
import com.skillhelper.application.models.FriendDto

fun Username.toFriendDto(imageSrc: String?): FriendDto =
    FriendDto(
        username = this.value,
        profileImage = imageSrc
    )