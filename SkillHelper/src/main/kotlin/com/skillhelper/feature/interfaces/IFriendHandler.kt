package com.skillhelper.feature.interfaces

import com.skillhelper.feature.models.FriendDto
import com.skillhelper.feature.models.RequestDto

interface IFriendHandler {
    fun acceptRequest(username: String, requestFrom: String);
    fun removeFriend(username: String, friend: String);
    fun addRequest(username: String, requestFrom: String);
    fun removeRequest(username: String, requestFrom: String);
    fun getFriends(username: String): List<FriendDto>;
    fun getRequests(username: String): List<RequestDto>;
}