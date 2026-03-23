package com.skillhelper.application.skillhandler

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.implementations.SkillHandler
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSkillsBySearchTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private val username1: Username = Username("test1");
    private val username2: Username = Username("test2");
    private lateinit var skill1: Skill;
    private lateinit var skill2: Skill;
    private val searchString = "test";

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        skill1 = Skill(SkillId(1), "skill 1", "description 1", StressLevel(1), null, username1, Visibility.PUBLIC)
        skill2 = Skill(SkillId(2), "skill 2", "description 2", StressLevel(1), "test", username2, Visibility.PUBLIC)
    }

    @Test
    fun getSkillsBySearch_NoSkills_ReturnsEmptyList() {
        every {
            skillRepository.getSkillsBySearch(searchString)
        } returns emptyList()

        val actual = handler.getSkillsBySearch(username1, searchString)

        assertThat(actual).isEmpty()
    }

    @Test
    fun getSkillsBySearch_HasSkills_AllPublic_ReturnsCorrectList() {
        val expected = listOf(skill1, skill2);
        every {
            skillRepository.getSkillsBySearch(searchString)
        } returns expected

        val actual = handler.getSkillsBySearch(username1, searchString)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getSkillsBySearch_HasSkills_PartlyFriendsOnly_NotFriendsWithAuthor_ReturnsCorrectList() {
        every {
            skillRepository.getSkillsBySearch(searchString)
        } returns listOf(skill1, skill2.copy(visibility = Visibility.FRIENDS_ONLY));

        every { friendRepository.getFriends(username1) } returns emptyList()

        val actual = handler.getSkillsBySearch(username1, searchString)
        val expected = listOf(skill1);

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getSkillsBySearch_HasSkills_PartlyFriendsOnly_FriendsWithAuthor_ReturnsCorrectList() {
        val skill2Modified = skill2.copy(visibility = Visibility.FRIENDS_ONLY);
        every {
            skillRepository.getSkillsBySearch(searchString)
        } returns listOf(skill1, skill2Modified);

        every { friendRepository.getFriends(username1) } returns listOf(username2);

        val actual = handler.getSkillsBySearch(username1, searchString)
        val expected = listOf(skill1, skill2Modified);

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getSkillsBySearch_HasSkills_PartlyPrivate_UserNotAuthor_ReturnsCorrectList() {
        every {
            skillRepository.getSkillsBySearch(searchString)
        } returns listOf(skill1, skill2.copy(visibility = Visibility.PRIVATE));

        val actual = handler.getSkillsBySearch(username1, searchString)
        val expected = listOf(skill1);

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getSkillsBySearch_HasSkills_PartlyPrivate_UserIsAuthor_ReturnsCorrectList() {
        val skill2Modified = skill2.copy(visibility = Visibility.PRIVATE)
        every {
            skillRepository.getSkillsBySearch(searchString)
        } returns listOf(skill1, skill2Modified);

        val actual = handler.getSkillsBySearch(username2, searchString)
        val expected = listOf(skill1, skill2Modified);

        assertThat(actual).isEqualTo(expected)
    }
}