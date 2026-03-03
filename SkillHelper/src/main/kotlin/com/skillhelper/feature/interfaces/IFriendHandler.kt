package com.skillhelper.feature.interfaces

import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.models.FriendDto
import com.skillhelper.feature.models.RequestDto

interface IFriendHandler {
    fun acceptRequest(username: Username, requestFrom: Username);
    fun removeFriend(username: Username, friend: Username);
    fun addRequest(username: Username, requestFrom: Username);
    fun removeRequest(username: Username, requestFrom: Username);
    fun getFriends(username: Username): List<FriendDto>;
    fun getRequests(username: Username): List<RequestDto>;
}