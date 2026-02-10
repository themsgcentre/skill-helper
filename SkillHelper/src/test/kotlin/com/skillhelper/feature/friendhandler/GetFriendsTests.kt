package com.skillhelper.feature.friendhandler

import com.skillhelper.feature.implementations.FriendHandler
import com.skillhelper.feature.models.FriendDto
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IRequestRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.models.UserDbo
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetFriendsTests {
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var userRepository: IUserRepository;
    private lateinit var requestRepository: IRequestRepository;
    private lateinit var handler: FriendHandler;
    private lateinit var username: String;
    private lateinit var mockFriends: List<UserDbo>


    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        requestRepository = mockk(relaxed = true)
        handler = FriendHandler(friendRepository, requestRepository, userRepository)

        username = "test user";

        mockFriends = listOf(
            UserDbo("friend 1", "", "src 1", ""),
            UserDbo("friend 2", "", "src 2", ""),
            UserDbo("friend 3", "", "src 3", ""),
            UserDbo("friend 4", "", null, ""),
        )
    }

    @Test
    fun getFriends_NoFriends_ReturnsEmptyList() {
        every { friendRepository.getFriends(username) } returns emptyList()

        val actual = handler.getFriends(username)

        assertThat(actual).isEmpty()
    }

    @Test
    fun getFriends_HasFriends_ReturnsCorrectList() {
        every { friendRepository.getFriends(username) } returns mockFriends.map{it.username}
        val usersByName = mockFriends.associateBy { it.username }

        every { userRepository.getUserByName(any()) } answers {
            usersByName[firstArg()]
        }

        val actual = handler.getFriends(username)
        val expected = mockFriends.map { user ->
            FriendDto(
                username = user.username,
                profileImage = user.profileImage
            )
        }

        assertThat(actual).isEqualTo(expected)
    }
}