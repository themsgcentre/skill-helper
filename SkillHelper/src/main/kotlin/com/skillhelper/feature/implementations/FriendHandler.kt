package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IFriendHandler
import org.springframework.stereotype.Service

@Service
class FriendHandler: IFriendHandler {
    override fun addFriend(username: String, friend: String) {
        TODO("Not yet implemented")
    }

    override fun removeFriend(username: String, friend: String) {
        TODO("Not yet implemented")
    }

    override fun addRequest(username: String, requestTo: String) {
        TODO("Not yet implemented")
    }

    override fun removeRequest(username: String, requestTo: String) {
        TODO("Not yet implemented")
    }

    override fun getFriends(username: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun getRequests(username: String): List<String> {
        TODO("Not yet implemented")
    }

}