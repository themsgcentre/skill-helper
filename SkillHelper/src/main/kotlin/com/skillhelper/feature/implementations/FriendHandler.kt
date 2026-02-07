package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IFriendHandler
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import org.springframework.stereotype.Service

@Service
class FriendHandler(
    val friendRepository: IFriendRepository,
    val requestRepository: IRequestRepository
): IFriendHandler {
    override fun acceptRequest(username: String, requestFrom: String) {
        val friendsOfSender = friendRepository.getFriends(username);
        val friendsOfRequester = requestRepository.getRequests(requestFrom);

        if(!friendsOfSender.contains(requestFrom)) {
            friendRepository.addFriend(username, requestFrom)
        }

        if(friendsOfRequester.contains(username)) {
            friendRepository.addFriend(requestFrom, username)
        }

        requestRepository.removeRequest(username, requestFrom)
        requestRepository.removeRequest(requestFrom, username)
    }

    override fun removeFriend(username: String, friend: String) {
        friendRepository.removeFriend(username, friend)
        friendRepository.removeFriend(friend, username)
    }

    override fun addRequest(username: String, requestFrom: String) {
        requestRepository.addRequest(username, requestFrom)
    }

    override fun removeRequest(username: String, requestFrom: String) {
        requestRepository.removeRequest(username, requestFrom)
    }

    override fun getFriends(username: String): List<String> {
        return friendRepository.getFriends(username);
    }

    override fun getRequests(username: String): List<String> {
        return requestRepository.getRequests(username);
    }

}