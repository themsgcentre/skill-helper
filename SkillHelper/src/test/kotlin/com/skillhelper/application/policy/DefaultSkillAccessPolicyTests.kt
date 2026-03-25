package com.skillhelper.application.policy

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.implementations.DefaultSkillAccessPolicy
import com.skillhelper.repository.interfaces.IFriendRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultSkillAccessPolicyTests {
    private lateinit var friendRepository: IFriendRepository
    private lateinit var policy: DefaultSkillAccessPolicy

    private val author = Username("author")
    private val viewer = Username("viewer")

    @BeforeEach
    fun setUp() {
        friendRepository = mockk(relaxed = true)
        policy = DefaultSkillAccessPolicy(friendRepository)
    }

    @Test
    fun canView_public_alwaysTrue() {
        val skill = skill(Visibility.PUBLIC)

        assertThat(policy.canView(viewer, skill)).isTrue()
        assertThat(policy.canView(author, skill)).isTrue()
    }

    @Test
    fun canView_private_onlyAuthor() {
        val skill = skill(Visibility.PRIVATE)

        assertThat(policy.canView(author, skill)).isTrue()
        assertThat(policy.canView(viewer, skill)).isFalse()
    }

    @Test
    fun canView_friendsOnly_authorAlwaysSeesOwnSkill() {
        val skill = skill(Visibility.FRIENDS_ONLY)

        assertThat(policy.canView(author, skill)).isTrue()
    }

    @Test
    fun canView_friendsOnly_viewerListedAsAuthorsFriend() {
        val skill = skill(Visibility.FRIENDS_ONLY)
        every { friendRepository.getFriends(author) } returns listOf(viewer)
        every { friendRepository.getFriends(viewer) } returns emptyList()

        assertThat(policy.canView(viewer, skill)).isTrue()
    }

    @Test
    fun canView_friendsOnly_authorListedAsViewersFriend() {
        val skill = skill(Visibility.FRIENDS_ONLY)
        every { friendRepository.getFriends(author) } returns emptyList()
        every { friendRepository.getFriends(viewer) } returns listOf(author)

        assertThat(policy.canView(viewer, skill)).isTrue()
    }

    @Test
    fun canView_friendsOnly_notFriends_returnsFalse() {
        val skill = skill(Visibility.FRIENDS_ONLY)
        every { friendRepository.getFriends(author) } returns emptyList()
        every { friendRepository.getFriends(viewer) } returns emptyList()

        assertThat(policy.canView(viewer, skill)).isFalse()
    }

    private fun skill(visibility: Visibility) =
        Skill(SkillId(1), "name", "description", StressLevel(1), null, author, visibility)
}
