package com.skillhelper.application.skillhandler

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.implementations.SkillHandler
import com.skillhelper.application.throwables.SkillAccessDeniedException
import com.skillhelper.application.throwables.SkillNotFoundException
import com.skillhelper.repository.implementations.SkillRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.IFriendRepository
import com.skillhelper.repository.interfaces.IUserRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSkillByIdTests {
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private val username1: Username = Username("test1");
    private val username2: Username = Username("test2");
    private lateinit var skill1: Skill;


    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        skill1 = Skill(SkillId(1), "skill 1", "description 1", StressLevel(1), null, username1, Visibility.PUBLIC)
    }

    @Test
    fun getSkillById_SkillExists_IsPublic_ReturnsCorrectSkill() {
        every { skillRepository.getSkillById(skill1.id!!) } returns skill1;

        val actual = handler.getSkillById(username2, skill1.id!!)

        assertThat(actual).isEqualTo(skill1)
    }

    @Test
    fun getSkillById_SkillDoesNotExist_ThrowsNotFound() {
        every { skillRepository.getSkillById(any()) } returns null

        assertThatThrownBy {
            handler.getSkillById(username2, skill1.id!!)
        } .isInstanceOf(SkillNotFoundException::class.java)
    }

    @Test
    fun getSkillById_SkillExists_FriendsOnly_NotFriends_ThrowsAccessDenied() {
        every { skillRepository.getSkillById(skill1.id!!) } returns skill1.copy(visibility = Visibility.FRIENDS_ONLY)
        every { friendRepository.getFriends(username2) } returns emptyList()

        assertThatThrownBy {
            handler.getSkillById(username2, skill1.id!!)
        } .isInstanceOf(SkillAccessDeniedException::class.java)
    }

    @Test
    fun getSkillById_SkillExists_FriendsOnly_IsFriends_ReturnsCorrectSkill() {
        val expected = skill1.copy(visibility = Visibility.FRIENDS_ONLY)
        every { skillRepository.getSkillById(skill1.id!!) } returns expected;
        every { friendRepository.getFriends(username2) } returns listOf(username1)

        val actual = handler.getSkillById(username2, skill1.id!!)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getSkillById_SkillExists_Private_NotAuthor_ThrowsAccessDenied() {
        val skillModified = skill1.copy(visibility = Visibility.PRIVATE)
        every { skillRepository.getSkillById(skill1.id!!) } returns skillModified;
        every { friendRepository.getFriends(username2) } returns listOf(username1)

        assertThatThrownBy {
            handler.getSkillById(username2, skill1.id!!)
        } .isInstanceOf(SkillAccessDeniedException::class.java)
    }

    @Test
    fun getSkillById_SkillExists_Private_IsAuthor_ReturnsCorrectSkill() {
        val expected = skill1.copy(visibility = Visibility.PRIVATE)
        every { skillRepository.getSkillById(skill1.id!!) } returns expected;

        val actual = handler.getSkillById(username1, skill1.id!!)

        assertThat(actual).isEqualTo(expected)
    }
}