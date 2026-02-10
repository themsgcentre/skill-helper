package com.skillhelper.feature.friendhandler

import com.skillhelper.feature.implementations.FriendHandler
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AcceptRequestTests {
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var requestRepository: IRequestRepository;
    private lateinit var handler: FriendHandler;
    private lateinit var username: String;
    private lateinit var requestFrom: String;


    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        requestRepository = mockk(relaxed = true)
        handler = FriendHandler(friendRepository, requestRepository, userRepository)

        username = "user 1";
        requestFrom = "user 2";

        every {
            friendRepository.getFriends(username)
        } returns emptyList()

        every {
            friendRepository.getFriends(requestFrom)
        } returns emptyList()
    }

    @Test
    fun acceptRequest_CallsRemoveOnRequestRepositoryForBothDirections() {
        handler.acceptRequest(username, requestFrom);

        verify(exactly = 1) { requestRepository.removeRequest(username, requestFrom) }
        verify(exactly = 1) { requestRepository.removeRequest(requestFrom, username) }
    }

    @Test
    fun acceptRequest_NeitherAreFriends_CallsAddOnBothOnRepository() {
        handler.acceptRequest(username, requestFrom);

        verify(exactly = 1) { friendRepository.addFriend(username, requestFrom) }
        verify(exactly = 1) { friendRepository.addFriend(requestFrom, username) }
    }

    @Test
    fun acceptRequest_RequesterNotFriendsWithReceiver_CallsAddFriendOnRepository() {
        every {
            friendRepository.getFriends(username)
        } returns listOf(requestFrom)

        handler.acceptRequest(username, requestFrom);
        verify(exactly = 1) { friendRepository.addFriend(requestFrom, username) }
    }

    @Test
    fun acceptRequest_ReceiverNotFriendsWithRequester_CallsAddFriendOnRepository() {
        every {
            friendRepository.getFriends(requestFrom)
        } returns listOf(username)

        handler.acceptRequest(username, requestFrom);
        verify(exactly = 1) { friendRepository.addFriend(username, requestFrom) }
    }

    @Test
    fun acceptRequest_UsersAreFriendsAlready_DoesNotCallAddFriendOnRepository() {
        every {
            friendRepository.getFriends(requestFrom)
        } returns listOf(username)

        every {
            friendRepository.getFriends(username)
        } returns listOf(requestFrom)

        handler.acceptRequest(username, requestFrom);
        verify(exactly = 0) { friendRepository.addFriend(any(), any()) }
    }
}