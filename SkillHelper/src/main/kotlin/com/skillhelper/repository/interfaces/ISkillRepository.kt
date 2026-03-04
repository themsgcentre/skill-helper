package com.skillhelper.repository.interfaces


import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Visibility

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