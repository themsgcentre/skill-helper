package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.ISkillHandler
import com.skillhelper.feature.models.SkillDto
import org.springframework.stereotype.Service

@Service
class SkillHandler: ISkillHandler {
    override fun getAllSkills(): List<SkillDto> {
        TODO("Not yet implemented")
    }

    override fun getSkillById(id: Long): SkillDto? {
        TODO("Not yet implemented")
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