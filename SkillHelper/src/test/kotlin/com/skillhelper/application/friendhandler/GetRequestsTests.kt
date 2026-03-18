package com.skillhelper.application.friendhandler

import com.skillhelper.application.implementations.FriendHandler
import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.Request
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRequestsTests {
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var requestRepository: IRequestRepository;
    private lateinit var handler: FriendHandler;
    private var username: Username = Username("test user");
    private lateinit var mockRequests: List<User>


    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        requestRepository = mockk(relaxed = true)
        handler = FriendHandler(friendRepository, requestRepository, userRepository)

        mockRequests = listOf(
            User(Username("request 1"), "", Profile("bio 1", "img1")),
            User(Username("request 2"), "", Profile("bio 2", "img2")),
            User(Username("request 3"), "", Profile("bio 3", "img3")),
            User(Username("request 4"), "", Profile("bio 4", null)),
        )

        every {
            userRepository.userExists(username)
        } returns true
    }

    @Test
    fun getRequests_NoRequests_ReturnsEmptyList() {
        every { requestRepository.getRequests(username) } returns emptyList()

        val actual = handler.getRequests(username)

        assertThat(actual).isEmpty()
    }

    @Test
    fun getRequests_HasRequests_ReturnsCorrectList() {
        every { requestRepository.getRequests(username) } returns mockRequests.map{it.username}
        val usersByName = mockRequests.associateBy { it.username }
        val names = usersByName.keys

        for (name in names) {
            every { userRepository.getUserByName(name) } returns usersByName[name]
        }

        val actual = handler.getRequests(username)
        val expected = mockRequests.map { user ->
            Request(
                username = user.username,
                image = user.profile.profileImage
            )
        }

        assertThat(actual).isEqualTo(expected)
    }
}