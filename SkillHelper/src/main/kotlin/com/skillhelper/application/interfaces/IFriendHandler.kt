package com.skillhelper.application.interfaces

import com.skillhelper.domain.entities.Profile
import com.skillhelper.domain.entities.Username

interface IFriendHandler {
    fun acceptRequest(username: Username, requestFrom: Username);
    fun removeFriend(username: Username, friend: Username);
    fun addRequest(username: Username, requestFrom: Username);
    fun removeRequest(username: Username, requestFrom: Username);
    fun getFriends(username: Username): List<Profile>;
    fun getRequests(username: Username): List<Profile>;
}