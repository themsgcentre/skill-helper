package com.skillhelper.api.mappers

import com.skillhelper.application.entities.Username
import com.skillhelper.api.models.FriendDto

fun Username.toFriendDto(imageSrc: String?): FriendDto =
    FriendDto(
        username = this.value,
        profileImage = imageSrc
    )