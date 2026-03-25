package com.skillhelper.application.implementations

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.interfaces.ISkillAccessPolicy
import com.skillhelper.repository.interfaces.IFriendRepository
import org.springframework.stereotype.Service

@Service
class DefaultSkillAccessPolicy(
    private val friendRepository: IFriendRepository,
) : ISkillAccessPolicy {
    override fun canView(viewer: Username, skill: Skill): Boolean =
        when (skill.visibility) {
            Visibility.FRIENDS_ONLY ->
                friendRepository.getFriends(skill.author).contains(viewer) ||
                    friendRepository.getFriends(viewer).contains(skill.author) ||
                    skill.author == viewer

            Visibility.PRIVATE -> skill.author == viewer
            else -> true
        }
}
