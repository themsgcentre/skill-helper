package com.skillhelper.repository.interfaces


import com.skillhelper.domain.entities.Skill
import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.StressLevel
import com.skillhelper.domain.entities.Visibility

interface ISkillRepository {
    fun getAllSkills() : List<Skill>;
    fun getSkillById(id: SkillId) : Skill?;
    fun getSkillsBySearch(searchString: String) : List<Skill>;
    fun getSkillsByStressLevel(minLevel: StressLevel, maxLevel: StressLevel) : List<Skill>;
    fun addSkill(skill: Skill): SkillId;
    fun updateSkill(skill: Skill);
    fun deleteSkill(skillId: SkillId);
    fun changeVisibility(skillId: SkillId, visibility: Visibility );
    fun skillExists(skillId: SkillId): Boolean;
}