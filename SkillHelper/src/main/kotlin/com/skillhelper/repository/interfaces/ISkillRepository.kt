package com.skillhelper.repository.interfaces


import com.skillhelper.repository.models.SkillDbo

interface ISkillRepository {
    fun getAllSkills() : List<SkillDbo>;
    fun getSkillById(id: Long) : SkillDbo?;
    fun getSkillsBySearch(searchString: String) : List<SkillDbo>;
    fun getSkillsByStressLevel(level: Int) : List<SkillDbo>;
    fun addSkill(skill: SkillDbo);
    fun updateSkill(skill: SkillDbo);
    fun deleteSkill(skillId: Long);
}