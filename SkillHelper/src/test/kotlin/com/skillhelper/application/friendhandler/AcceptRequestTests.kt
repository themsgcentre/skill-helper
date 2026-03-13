package com.skillhelper.application.friendhandler

import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.FriendHandler
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.Called
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
    private var username: Username = Username("user 1");
    private var requestFrom: Username = Username("user 2");


    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        requestRepository = mockk(relaxed = true)
        handler = FriendHandler(friendRepository, requestRepository, userRepository)

        every {
            friendRepository.getFriends(username)
        } returns emptyList()

        every {
            friendRepository.getFriends(requestFrom)
        } returns emptyList()

        every {
            userRepository.userExists(username)
        } returns true

        every {
            userRepository.userExists(requestFrom)
        } returns true;
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

    @Test
    fun acceptRequest_ReceiverDoesNotExist_DoesNotCallFriendOrRequestRepository() {
        every {
            userRepository.userExists(username)
        } returns false

        handler.acceptRequest(username, requestFrom);

        verify { friendRepository wasNot Called }
        verify { requestRepository wasNot Called }
    }

    @Test
    fun acceptRequest_RequesterDoesNotExist_DoesNotCallFriendOrRequestRepository() {
        every {
            userRepository.userExists(requestFrom)
        } returns false

        handler.acceptRequest(username, requestFrom);

        verify { friendRepository wasNot Called }
        verify { requestRepository wasNot Called }
    }

    @Test
    fun acceptRequest_UsernamesAreEqual_DoesNotCallRepositories() {
        handler.acceptRequest(username, username);

        verify { friendRepository wasNot Called }
        verify { requestRepository wasNot Called }
    }
}