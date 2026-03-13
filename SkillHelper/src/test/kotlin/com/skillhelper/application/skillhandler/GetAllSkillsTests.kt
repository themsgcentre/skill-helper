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

class GetAllSkillsTests {
    // TODO: add tests for visibility
    private lateinit var userRepository: IUserRepository;
    private lateinit var skillRepository: SkillRepository;
    private lateinit var favoriteRepository: IFavoriteRepository;
    private lateinit var friendRepository: IFriendRepository;
    private lateinit var handler: SkillHandler;
    private lateinit var mockSkills: List<Skill>
    private var username: Username = Username("test");

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        skillRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        friendRepository = mockk(relaxed = true)
        handler = SkillHandler(skillRepository, favoriteRepository, userRepository, friendRepository)

        mockSkills = listOf(
            Skill(SkillId(1), "skill 1", "description 1", StressLevel(1), null, username, Visibility.FRIENDS_ONLY),
            Skill(SkillId(2), "skill 2", "description 2", StressLevel(1), "test", Username("other"), Visibility.FRIENDS_ONLY),
        )
    }

    @Test
    fun getAllSkills_NoSkills_ReturnsEmptyList() {
        every {
            skillRepository.getAllSkills()
        } returns emptyList()

        val actual = handler.getAllSkills(username)

        assertThat(actual).isEmpty()
    }

    @Test
    fun getAllSkills_HasSkills_ReturnsCorrectList() {
        every {
            skillRepository.getAllSkills()
        } returns mockSkills

        val actual = handler.getAllSkills(username)
        val expected = mockSkills

        assertThat(actual).isEqualTo(expected)
    }
}