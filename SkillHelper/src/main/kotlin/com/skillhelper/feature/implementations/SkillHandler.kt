package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.ISkillHandler
import com.skillhelper.feature.models.SkillDto
import com.skillhelper.feature.models.VisibilityDto
import com.skillhelper.repository.implementations.FavoriteRepository
import com.skillhelper.repository.implementations.VisibilityRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.interfaces.IVisibilityRepository
import org.springframework.stereotype.Service

@Service
class SkillHandler(
    val skillRepository: ISkillRepository,
    val favoriteRepository: IFavoriteRepository,
    val userRepository: IUserRepository,
    val visibilityRepository: IVisibilityRepository
): ISkillHandler {
    override fun getAllSkills(): List<SkillDto> {
        return skillRepository
            .getAllSkills()
            .map { it.toDto() }
    }

    override fun getSkillById(id: Long): SkillDto? {
        return skillRepository.getSkillById(id)?.toDto()
    }

    override fun getSkillsBySearch(searchString: String): List<SkillDto> {
        return skillRepository.getSkillsBySearch(searchString).map { it.toDto() }
    }

    override fun getSkillsByStressLevel(minLevel: Int, maxLevel: Int): List<SkillDto> {
        return skillRepository.getSkillsByStressLevel(minLevel, maxLevel).map { it.toDto() }
    }

    override fun addSkill(skill: SkillDto): Long {
        if(skill.author != null && !userRepository.userExists(skill.author)) return -1;
        if(skill.stressLevel !in 0..100) return -1;
        return skillRepository.addSkill(skill.toDbo())
    }

    override fun updateSkill(skill: SkillDto) {
        if(skill.author != null && !userRepository.userExists(skill.author)) return;
        if(skill.stressLevel !in 0..100) return;
        skillRepository.updateSkill(skill.toDbo())
    }

    override fun deleteSkill(skillId: Long) {
        skillRepository.deleteSkill(skillId)
    }

    override fun addFavorite(username: String, skillId: Long) {
        if(!userRepository.userExists(username) || !skillRepository.skillExists(skillId)) return;
        favoriteRepository.addFavorite(username, skillId)
    }

    override fun removeFavorite(username: String, skillId: Long) {
        favoriteRepository.removeFavorite(username, skillId)
    }

    override fun changeVisibility(skillId: Long, visibility: Long) {
        skillRepository.changeVisibility(skillId, visibility)
    }

    override fun getVisibilities(): List<VisibilityDto> {
        return visibilityRepository.getAllVisibilityLevels().map { it.toDto() }
    }
}