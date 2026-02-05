package com.skillhelper.repository.implementations

import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.models.SkillDbo
import org.springframework.stereotype.Service

@Service
class SkillRepository: ISkillRepository {
    override fun getAllSkills(): List<SkillDbo> {
        TODO("Not yet implemented")
    }

    override fun getSkillById(id: Long): SkillDbo? {
        TODO("Not yet implemented")
    }

    override fun getSkillsBySearch(searchString: String): List<SkillDbo> {
        TODO("Not yet implemented")
    }

    override fun getSkillsByStressLevel(
        minLevel: Int,
        maxLevel: Int
    ): List<SkillDbo> {
        TODO("Not yet implemented")
    }

    override fun addSkill(skill: SkillDbo) {
        TODO("Not yet implemented")
    }

    override fun updateSkill(skill: SkillDbo) {
        TODO("Not yet implemented")
    }

    override fun deleteSkill(skillId: Long) {
        TODO("Not yet implemented")
    }

    override fun changeVisibility(skillId: Long, visibilityId: Long) {
        TODO("Not yet implemented")
    }
}