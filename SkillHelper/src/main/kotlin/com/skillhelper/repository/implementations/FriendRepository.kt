package com.skillhelper.repository.implementations

import com.skillhelper.repository.interfaces.IFriendRepository
import org.springframework.stereotype.Service

@Service
class FriendRepository: IFriendRepository {
    override fun addFriend(username: String, friend: String) {
        TODO("Not yet implemented")
    }

    override fun removeFriend(username: String, friend: String) {
        TODO("Not yet implemented")
    }

    override fun getFriends(username: String): List<String> {
        TODO("Not yet implemented")
    }

}