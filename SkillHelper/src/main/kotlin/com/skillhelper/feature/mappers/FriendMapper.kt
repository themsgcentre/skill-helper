package com.skillhelper.feature.mappers

import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.models.FriendDto

fun Username.toFriendDto(imageSrc: String?): FriendDto =
    FriendDto(
        username = this.value,
        profileImage = imageSrc
    )