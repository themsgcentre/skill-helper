package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.ISkillHandler
import com.skillhelper.feature.models.SkillDto
import com.skillhelper.repository.interfaces.ISkillRepository
import org.springframework.stereotype.Service

@Service
class SkillHandler(val skillRepository: ISkillRepository): ISkillHandler {
    override fun getAllSkills(): List<SkillDto> {
        return skillRepository
            .getAllSkills()
            .map { it.toDto() }
    }

    override fun getSkillById(id: Long): SkillDto? {
        return skillRepository.getSkillById(id)?.toDto()
    }

    override fun getSkillsBySearch(searchString: String): List<SkillDto> {
        TODO("Not yet implemented")
    }

    override fun getSkillsByStressLevel(level: Int): List<SkillDto> {
        TODO("Not yet implemented")
    }

    override fun addSkill(skill: SkillDto) {
        TODO("Not yet implemented")
    }

    override fun updateSkill(skill: SkillDto) {
        TODO("Not yet implemented")
    }

    override fun deleteSkill(skillId: Long) {
        TODO("Not yet implemented")
    }

    override fun addFavorite(username: String, skillId: Long) {
        TODO("Not yet implemented")
    }

    override fun removeFavorite(username: String, skillId: Long) {
        TODO("Not yet implemented")
    }

    override fun changeVisibility(skillId: Long, visibility: Long) {
        TODO("Not yet implemented")
    }

}