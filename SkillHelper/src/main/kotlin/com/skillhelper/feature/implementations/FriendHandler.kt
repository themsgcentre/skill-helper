package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IFriendHandler
import com.skillhelper.feature.models.FriendDto
import com.skillhelper.feature.models.RequestDto
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service

@Service
class FriendHandler(
    val friendRepository: IFriendRepository,
    val requestRepository: IRequestRepository,
    val userRepository: IUserRepository,
): IFriendHandler {
    override fun acceptRequest(username: String, requestFrom: String) {
        val friendsOfReceiver = friendRepository.getFriends(username);
        val friendsOfRequester = friendRepository.getFriends(requestFrom);

        if(!friendsOfReceiver.contains(requestFrom)) {
            friendRepository.addFriend(username, requestFrom);
        }

        if(!friendsOfRequester.contains(username)) {
            friendRepository.addFriend(requestFrom, username);
        }

        requestRepository.removeRequest(username, requestFrom);
        requestRepository.removeRequest(requestFrom, username);
    }

    override fun removeFriend(username: String, friend: String) {
        friendRepository.removeFriend(username, friend);
        friendRepository.removeFriend(friend, username);
    }

    override fun addRequest(username: String, requestFrom: String) {
        requestRepository.addRequest(username, requestFrom);
    }

    override fun removeRequest(username: String, requestFrom: String) {
        requestRepository.removeRequest(username, requestFrom);
    }

    override fun getFriends(username: String): List<FriendDto> {
        val friendNames = friendRepository.getFriends(username);

        return friendNames.map { friendUsername ->
            val user = userRepository.getUserByName(friendUsername);
            friendUsername.toFriendDto(user?.profileImage);
        }
    }

    override fun getRequests(username: String): List<RequestDto> {
        val requestNames = requestRepository.getRequests(username)

        return requestNames.map { requestUsername ->
            val user = userRepository.getUserByName(requestUsername);
            requestUsername.toRequestDto(user?.profileImage);
        }
    }

}