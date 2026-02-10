package com.skillhelper.feature.friendhandler

import com.skillhelper.feature.implementations.FriendHandler
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddRequestTests {
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

        every {
            userRepository.userExists(username)
        } returns true

        every {
            userRepository.userExists(requestFrom)
        } returns true
    }

    @Test
    fun addRequest_UsersAreFriendsAlreadyOnReceiverSide_DoesNotCallRequestRepository() {
        every {
            friendRepository.getFriends(username)
        } returns listOf(requestFrom)

        handler.addRequest(username, requestFrom)

        verify { requestRepository wasNot Called }
    }

    @Test
    fun addRequest_UsersAreFriendsAlreadyOnRequesterSide_DoesNotCallRequestRepository() {
        every {
            friendRepository.getFriends(requestFrom)
        } returns listOf(username)

        handler.addRequest(username, requestFrom)

        verify { requestRepository wasNot Called }
    }

    @Test
    fun addRequest_ReceiverDoesNotExist_DoesNotCallRequestOrFriendRepository() {
        every {
            userRepository.userExists(username)
        } returns false

        handler.addRequest(username, requestFrom)

        verify { friendRepository wasNot Called }
        verify { requestRepository wasNot Called }
    }

    @Test
    fun addRequest_RequesterDoesNotExist_DoesNotCallRequestOrFriendRepository() {
        every {
            userRepository.userExists(requestFrom)
        } returns false

        handler.addRequest(username, requestFrom)

        verify { friendRepository wasNot Called }
        verify { requestRepository wasNot Called }
    }

    @Test
    fun addRequest_RequestAlreadyExists_DoesNotCallAddOnRepository() {
        every {
            requestRepository.getRequests(username)
        } returns listOf(requestFrom)

        handler.addRequest(username, requestFrom)

        verify(exactly = 0){ requestRepository.addRequest(any(), any()) }
    }

    @Test
    fun addRequest_ReceiverHasSentRequestToRequester_AddsFriendAndDoesNotCallAddRequestOnRepository() {
        every {
            requestRepository.getRequests(requestFrom)
        } returns listOf(username)

        handler.addRequest(username, requestFrom)

        verify(exactly = 1) { friendRepository.addFriend(username, requestFrom) }
        verify(exactly = 1) { friendRepository.addFriend(requestFrom, username) }
        verify(exactly = 0) { requestRepository.addRequest(any(), any()) }
    }

    @Test
    fun addRequest_BothUsernamesAreEqual_DoesNotCallRepositories() {
        handler.addRequest(username, username)

        verify { friendRepository wasNot Called }
        verify { requestRepository wasNot Called }
    }
}