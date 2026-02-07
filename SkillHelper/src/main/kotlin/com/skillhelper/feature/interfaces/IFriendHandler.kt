package com.skillhelper.feature.interfaces

interface IFriendHandler {
    fun addFriend(username: String, friend: String);
    fun removeFriend(username: String, friend: String);
    fun addRequest(username: String, requestTo: String);
    fun removeRequest(username: String, requestTo: String);
    fun getFriends(username: String): List<String>;
    fun getRequests(username: String): List<String>;
}