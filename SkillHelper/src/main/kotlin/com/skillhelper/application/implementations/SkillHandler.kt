package com.skillhelper.application.implementations

import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.interfaces.ISkillAccessPolicy
import com.skillhelper.application.interfaces.ISkillHandler
import com.skillhelper.application.throwables.AuthorNotFoundException
import com.skillhelper.application.throwables.SkillNotFoundException
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service
import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.Visibility
import com.skillhelper.application.throwables.InvalidSkillOperationException
import com.skillhelper.application.throwables.SkillAccessDeniedException

@Service
class SkillHandler(
    val skillRepository: ISkillRepository,
    val favoriteRepository: IFavoriteRepository,
    val userRepository: IUserRepository,
    private val skillAccessPolicy: ISkillAccessPolicy,
): ISkillHandler {
    override fun getAllSkills(username: Username): List<Skill> {
        return skillRepository
            .getAllSkills()
            .filter { skillAccessPolicy.canView(username, it) }
    }

    override fun getSkillById(username: Username, id: SkillId): Skill {
        val skill = skillRepository.getSkillById(id) ?: throw SkillNotFoundException();
        if(skillAccessPolicy.canView(username, skill)) {
            return skill;
        }
        throw SkillAccessDeniedException()
    }

    override fun getSkillsBySearch(username: Username, searchString: String): List<Skill> {
        return skillRepository.getSkillsBySearch(searchString)
            .filter { skillAccessPolicy.canView(username, it) }
    }

    override fun getSkillsByStressLevel(username: Username, minLevel: StressLevel, maxLevel: StressLevel): List<Skill> {
        return skillRepository.getSkillsByStressLevel(minLevel, maxLevel)
            .filter { skillAccessPolicy.canView(username, it) }
    }

    override fun addSkill(username: Username, skill: Skill): SkillId {
        if(!userRepository.userExists(skill.author)) throw AuthorNotFoundException();
        if(skill.author != username) throw InvalidSkillOperationException();
        return skillRepository.addSkill(skill)
    }

    override fun updateSkill(username: Username, skill: Skill) {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        val originalSkill = skillRepository.getSkillById(skill.id!!) ?: throw SkillNotFoundException();
        if(username != originalSkill.author) throw SkillAccessDeniedException();
        if(originalSkill.author != skill.author) throw InvalidSkillOperationException();
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
            .filter { skillAccessPolicy.canView(username, it) }
    }

    override fun changeVisibility(username: Username, skillId: SkillId, visibility: Visibility) {
        val originalSkill = skillRepository.getSkillById(skillId) ?: throw SkillNotFoundException();
        if(originalSkill.author != username) throw SkillAccessDeniedException();
        skillRepository.changeVisibility(skillId, visibility)
    }

    override fun getVisibilities(): List<Visibility> {
        return Visibility.entries.toList();
    }
}