package com.skillhelper.api.mappers

import com.skillhelper.api.models.FriendDto
import com.skillhelper.application.entities.Friend

fun Friend.toDto(): FriendDto = FriendDto(
    username = this.username.value,
    profileImage = this.image,
)