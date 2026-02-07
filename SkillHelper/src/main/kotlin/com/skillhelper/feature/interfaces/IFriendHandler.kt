package com.skillhelper.feature.interfaces

interface IFriendHandler {
    fun acceptRequest(username: String, requestFrom: String);
    fun removeFriend(username: String, friend: String);
    fun addRequest(username: String, requestFrom: String);
    fun removeRequest(username: String, requestFrom: String);
    fun getFriends(username: String): List<String>;
    fun getRequests(username: String): List<String>;
}