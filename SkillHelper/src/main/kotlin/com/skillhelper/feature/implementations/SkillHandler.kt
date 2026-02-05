package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.ISkillHandler
import com.skillhelper.feature.models.SkillDto
import com.skillhelper.repository.implementations.FavoriteRepository
import com.skillhelper.repository.implementations.VisibilityRepository
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import org.springframework.stereotype.Service

@Service
class SkillHandler(
    val skillRepository: ISkillRepository,
    val favoriteRepository: IFavoriteRepository,
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

    // TODO: check if author exists when not null for foreign key constraint
    override fun addSkill(skill: SkillDto) {
        skillRepository.addSkill(skill.toDbo())
    }

    override fun updateSkill(skill: SkillDto) {
        skillRepository.updateSkill(skill.toDbo())
    }

    override fun deleteSkill(skillId: Long) {
        skillRepository.deleteSkill(skillId)
    }

    // TODO: check if skills and user exists bc of foreign key constraint!!
    override fun addFavorite(username: String, skillId: Long) {
        favoriteRepository.addFavorite(username, skillId)
    }

    override fun removeFavorite(username: String, skillId: Long) {
        favoriteRepository.removeFavorite(username, skillId)
    }

    override fun changeVisibility(skillId: Long, visibility: Long) {
        skillRepository.changeVisibility(skillId, visibility)
    }

}