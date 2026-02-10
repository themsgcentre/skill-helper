package com.skillhelper.feature.friendhandler

import com.skillhelper.feature.implementations.FriendHandler
import com.skillhelper.feature.models.RequestDto
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.models.UserDbo
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
    private lateinit var username: String;
    private lateinit var mockRequests: List<UserDbo>


    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        requestRepository = mockk(relaxed = true)
        handler = FriendHandler(friendRepository, requestRepository, userRepository)

        username = "test user";

        mockRequests = listOf(
            UserDbo("request 1", "", "src 1", ""),
            UserDbo("request 2", "", "src 2", ""),
            UserDbo("request 3", "", "src 3", ""),
            UserDbo("request 4", "", null, ""),
        )
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

        every { userRepository.getUserByName(any()) } answers {
            usersByName[firstArg()]
        }

        val actual = handler.getRequests(username)
        val expected = mockRequests.map { user ->
            RequestDto(
                username = user.username,
                profileImage = user.profileImage
            )
        }

        assertThat(actual).isEqualTo(expected)
    }
}