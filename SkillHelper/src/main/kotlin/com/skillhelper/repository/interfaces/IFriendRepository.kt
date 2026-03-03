package com.skillhelper.repository.interfaces

import com.skillhelper.domain.entities.Username

interface IFriendRepository {
    fun addFriend(username: Username, friend: Username);
    fun removeFriend(username: Username, friend: Username);
    fun getFriends(username: Username): List<Username>;
}