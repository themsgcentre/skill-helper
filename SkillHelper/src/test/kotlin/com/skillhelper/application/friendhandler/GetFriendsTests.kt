package com.skillhelper.application.friendhandler

import com.skillhelper.application.implementations.FriendHandler
import com.skillhelper.api.models.FriendDto
import com.skillhelper.application.entities.Friend
import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
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
    private val username: Username = Username("test user");
    private lateinit var mockFriends: List<User>


    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        requestRepository = mockk(relaxed = true)
        handler = FriendHandler(friendRepository, requestRepository, userRepository)


        mockFriends = listOf(
            User(Username("friend 1"), "", Profile("bio 1", "img1")),
            User(Username("friend 2"), "", Profile("bio 2", "img2")),
            User(Username("friend 3"), "", Profile("bio 3", "img3")),
            User(Username("friend 4"), "", Profile("bio 4", null)),
        )

        every {
            userRepository.userExists(username)
        } returns true
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
        val names = usersByName.keys

        for (name in names) {
            every { userRepository.getUserByName(name) } returns usersByName[name]
        }

        val actual = handler.getFriends(username)
        val expected = mockFriends.map { user ->
            Friend(
                username = user.username,
                image = user.profile.profileImage,
            )
        }

        assertThat(actual).isEqualTo(expected)
    }
}