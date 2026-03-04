package com.skillhelper.application.interfaces

import com.skillhelper.application.entities.Friend
import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.Request
import com.skillhelper.application.entities.Username

interface IFriendHandler {
    fun acceptRequest(username: Username, requestFrom: Username);
    fun removeFriend(username: Username, friend: Username);
    fun addRequest(username: Username, requestFrom: Username);
    fun removeRequest(username: Username, requestFrom: Username);
    fun getFriends(username: Username): List<Friend>;
    fun getRequests(username: Username): List<Request>;
}