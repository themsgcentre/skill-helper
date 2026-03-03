package com.skillhelper.application.implementations

import com.skillhelper.domain.entities.Username
import com.skillhelper.application.interfaces.IFriendHandler
import com.skillhelper.application.mappers.toFriendDto
import com.skillhelper.application.mappers.toRequestDto
import com.skillhelper.application.models.FriendDto
import com.skillhelper.application.models.RequestDto
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
    override fun acceptRequest(username: Username, requestFrom: Username) {
        if(!userRepository.userExists(username) || !userRepository.userExists(requestFrom) || username == requestFrom) return;

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

    override fun removeFriend(username: Username, friend: Username) {
        friendRepository.removeFriend(username, friend);
        friendRepository.removeFriend(friend, username);
    }

    override fun addRequest(username: Username, requestFrom: Username) {
        if(!userRepository.userExists(username) || !userRepository.userExists(requestFrom) || username == requestFrom) return;
        if(friendRepository.getFriends(username).contains(requestFrom) || friendRepository.getFriends(requestFrom).contains(username)) return;

        if(requestRepository.getRequests(username).contains(requestFrom)) return;

        if(requestRepository.getRequests(requestFrom).contains(username)) {
            friendRepository.addFriend(username, requestFrom);
            friendRepository.addFriend(requestFrom, username);
            return;
        }

        requestRepository.addRequest(username, requestFrom);
    }

    override fun removeRequest(username: Username, requestFrom: Username) {
        requestRepository.removeRequest(username, requestFrom);
    }

    override fun getFriends(username: Username): List<FriendDto> {
        val friendNames = friendRepository.getFriends(username);

        return friendNames.map { friendUsername ->
            val user = userRepository.getUserByName(friendUsername);
            friendUsername.toFriendDto(user?.profile?.profileImage);
        }
    }

    override fun getRequests(username: Username): List<RequestDto> {
        val requestNames = requestRepository.getRequests(username)

        return requestNames.map { requestUsername ->
            val user = userRepository.getUserByName(requestUsername);
            requestUsername.toRequestDto(user?.profile?.profileImage);
        }
    }

}