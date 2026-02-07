package com.skillhelper.repository.interfaces

interface IFriendRepository {
    fun addFriend(username: String, friend: String);
    fun removeFriend(username: String, friend: String);
    fun getFriends(username: String): List<String>;
}