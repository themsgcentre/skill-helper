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

@Service
class SkillHandler(
    val skillRepository: ISkillRepository,
    val favoriteRepository: IFavoriteRepository,
    val userRepository: IUserRepository,
): ISkillHandler {
    //TODO: add get favorites for user
    override fun getAllSkills(): List<Skill> {
        //TODO: check visibilities for specific user
        return skillRepository.getAllSkills()
    }

    override fun getSkillById(id: SkillId): Skill? {
        //TODO: return null if user is not allowed to see skill
        return skillRepository.getSkillById(id)
    }

    override fun getSkillsBySearch(searchString: String): List<Skill> {
        //TODO: check visibilities for specific user
        return skillRepository.getSkillsBySearch(searchString)
    }

    override fun getSkillsByStressLevel(minLevel: StressLevel, maxLevel: StressLevel): List<Skill> {
        //TODO: check visibilities for specific user
        return skillRepository.getSkillsByStressLevel(minLevel, maxLevel)
    }

    override fun addSkill(skill: Skill): SkillId {
        if(skill.author != null && !userRepository.userExists(skill.author)) throw AuthorNotFoundException();
        return skillRepository.addSkill(skill)
    }

    override fun updateSkill(skill: Skill) {
        if(!skillRepository.skillExists(skill.id!!)) throw SkillNotFoundException();
        if(skill.author != null && !userRepository.userExists(skill.author)) throw AuthorNotFoundException();
        skillRepository.updateSkill(skill)
    }

    override fun deleteSkill(skillId: SkillId) {
        skillRepository.deleteSkill(skillId)
    }

    override fun addFavorite(username: Username, skillId: SkillId) {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        if(!skillRepository.skillExists(skillId)) throw SkillNotFoundException();
        favoriteRepository.addFavorite(username, skillId)
    }

    override fun removeFavorite(username: Username, skillId: SkillId) {
        favoriteRepository.removeFavorite(username, skillId)
    }

    override fun changeVisibility(skillId: SkillId, visibility: Visibility) {
        if(!skillRepository.skillExists(skillId)) throw SkillNotFoundException();
        skillRepository.changeVisibility(skillId, visibility)
    }

    override fun getVisibilities(): List<Visibility> {
        return Visibility.entries.toList();
    }
}