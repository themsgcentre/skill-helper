package com.skillhelper.application.friendhandler

import com.skillhelper.application.entities.Username
import com.skillhelper.application.implementations.FriendHandler
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveRequestTests {
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
    }

    @Test
    fun removeFriend_CallsRemoveOnRepository() {
        handler.removeRequest(username, requestFrom)

        verify(exactly = 1) { requestRepository.removeRequest(username, requestFrom) }
    }
}