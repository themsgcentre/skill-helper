package com.skillhelper.application.implementations

import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.StressLevel
import com.skillhelper.domain.entities.Username
import com.skillhelper.domain.entities.Visibility
import com.skillhelper.application.interfaces.ISkillHandler
import com.skillhelper.api.mappers.toDomain
import com.skillhelper.api.mappers.toDto
import com.skillhelper.application.models.SkillDto
import com.skillhelper.application.models.VisibilityDto
import com.skillhelper.repository.interfaces.IFavoriteRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service

@Service
class SkillHandler(
    val skillRepository: ISkillRepository,
    val favoriteRepository: IFavoriteRepository,
    val userRepository: IUserRepository,
): ISkillHandler {
    override fun getAllSkills(): List<SkillDto> {
        return skillRepository
            .getAllSkills()
            .map { it.toDto() }
    }

    override fun getSkillById(id: SkillId): SkillDto? {
        return skillRepository.getSkillById(id)?.toDto()
    }

    override fun getSkillsBySearch(searchString: String): List<SkillDto> {
        return skillRepository.getSkillsBySearch(searchString).map { it.toDto() }
    }

    override fun getSkillsByStressLevel(minLevel: StressLevel, maxLevel: StressLevel): List<SkillDto> {
        return skillRepository.getSkillsByStressLevel(minLevel, maxLevel).map { it.toDto() }
    }

    override fun addSkill(skill: SkillDto): SkillId {
        if(skill.author != null && !userRepository.userExists(Username(skill.author))) return SkillId(-1) ;
        if(skill.stressLevel !in 0..100) return SkillId(-1);
        return skillRepository.addSkill(skill.toDomain())
    }

    override fun updateSkill(skill: SkillDto) {
        if(skill.author != null && !userRepository.userExists(Username(skill.author))) return;
        if(skill.stressLevel !in 0..100) return;
        skillRepository.updateSkill(skill.toDomain())
    }

    override fun deleteSkill(skillId: SkillId) {
        skillRepository.deleteSkill(skillId)
    }

    override fun addFavorite(username: Username, skillId: SkillId) {
        if(!userRepository.userExists(username) || !skillRepository.skillExists(skillId)) return;
        favoriteRepository.addFavorite(username, skillId)
    }

    override fun removeFavorite(username: Username, skillId: SkillId) {
        favoriteRepository.removeFavorite(username, skillId)
    }

    override fun changeVisibility(skillId: SkillId, visibility: Visibility) {
        skillRepository.changeVisibility(skillId, visibility)
    }

    override fun getVisibilities(): List<VisibilityDto> {
        return Visibility.entries.map { it.toDto() }
    }
}