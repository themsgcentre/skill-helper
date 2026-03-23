package com.skillhelper.application.implementations

import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.interfaces.ISkillHandler
import com.skillhelper.application.throwables.AuthorNotFoundException
import com.skillhelper.application.throwables.SkillNotFoundException
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.throwables.SkillAccessDeniedException
import com.skillhelper.repository.interfaces.IFriendRepository

@Service
class SkillHandler(
    val skillRepository: ISkillRepository,
    val favoriteRepository: IFavoriteRepository,
    val userRepository: IUserRepository,
    val friendRepository: IFriendRepository,
): ISkillHandler {
    override fun getAllSkills(username: Username): List<Skill> {
        return skillRepository
            .getAllSkills()
            .filter { skillAvailable(username, it) }
    }

    override fun getSkillById(username: Username, id: SkillId): Skill {
        val skill = skillRepository.getSkillById(id) ?: throw SkillNotFoundException();
        if(skillAvailable(username, skill)) {
            return skill;
        }
        throw SkillAccessDeniedException()
    }

    override fun getSkillsBySearch(username: Username, searchString: String): List<Skill> {
        return skillRepository.getSkillsBySearch(searchString)
            .filter { skillAvailable(username, it) }
    }

    override fun getSkillsByStressLevel(username: Username, minLevel: StressLevel, maxLevel: StressLevel): List<Skill> {
        return skillRepository.getSkillsByStressLevel(minLevel, maxLevel)
            .filter { skillAvailable(username, it) }
    }

    override fun addSkill(skill: Skill): SkillId {
        if(!userRepository.userExists(skill.author)) throw AuthorNotFoundException();
        return skillRepository.addSkill(skill)
    }

    override fun updateSkill(skill: Skill) {
        val originalSkill = skillRepository.getSkillById(skill.id!!) ?: throw SkillNotFoundException();
        if(originalSkill.author != skill.author) throw SkillAccessDeniedException();
        if(!userRepository.userExists(skill.author)) throw AuthorNotFoundException();
        skillRepository.updateSkill(skill)
    }

    override fun deleteSkill(username: Username, skillId: SkillId) {
        val originalSkill = skillRepository.getSkillById(skillId) ?: throw SkillNotFoundException();
        if(originalSkill.author != username) throw SkillAccessDeniedException();
        skillRepository.deleteSkill(skillId)
    }

    override fun addFavorite(username: Username, skillId: SkillId) {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        if(!skillRepository.skillExists(skillId)) throw SkillNotFoundException();

        val favorites = favoriteRepository.getFavorites(username)
        if(favorites.contains(skillId)) return;
        favoriteRepository.addFavorite(username, skillId)
    }

    override fun removeFavorite(username: Username, skillId: SkillId) {
        favoriteRepository.removeFavorite(username, skillId)
    }

    override fun getFavorites(username: Username): List<Skill> {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        return favoriteRepository.getFavorites(username)
            .mapNotNull { skillRepository.getSkillById(it) }
            .filter { skillAvailable(username, it) }
    }

    override fun changeVisibility(username: Username, skillId: SkillId, visibility: Visibility) {
        val originalSkill = skillRepository.getSkillById(skillId) ?: throw SkillNotFoundException();
        if(originalSkill.author != username) throw SkillAccessDeniedException();
        if(!skillRepository.skillExists(skillId)) throw SkillNotFoundException();
        skillRepository.changeVisibility(skillId, visibility)
    }

    override fun getVisibilities(): List<Visibility> {
        return Visibility.entries.toList();
    }

    private fun skillAvailable(username: Username, skill: Skill): Boolean {
        when (skill.visibility) {
            Visibility.FRIENDS_ONLY -> {
                val friends = friendRepository.getFriends(skill.author)
                return friends.contains(username) || skill.author == username
            }
            Visibility.PRIVATE -> {
                return skill.author == username
            }
            else -> {
                return true;
            }
        }
    }
}