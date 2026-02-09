package com.skillhelper.repository.interfaces


import com.skillhelper.repository.models.SkillDbo
import com.skillhelper.repository.models.VisibilityDbo

interface ISkillRepository {
    fun getAllSkills() : List<SkillDbo>;
    fun getSkillById(id: Long) : SkillDbo?;
    fun getSkillsBySearch(searchString: String) : List<SkillDbo>;
    fun getSkillsByStressLevel(minLevel: Int, maxLevel: Int) : List<SkillDbo>;
    fun addSkill(skill: SkillDbo): Long;
    fun updateSkill(skill: SkillDbo);
    fun deleteSkill(skillId: Long);
    fun changeVisibility(skillId: Long, visibilityId: Long );
}