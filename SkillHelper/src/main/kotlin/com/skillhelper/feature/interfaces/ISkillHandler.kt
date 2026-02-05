package com.skillhelper.feature.interfaces

import com.skillhelper.feature.models.SkillDto

interface ISkillHandler {
    fun getAllSkills() : List<SkillDto>;
    fun getSkillById(id: Long) : SkillDto?;
    fun getSkillsBySearch(searchString: String) : List<SkillDto>;
    fun getSkillsByStressLevel(minLevel: Int, maxLevel: Int) : List<SkillDto>;
    fun addSkill(skill: SkillDto);
    fun updateSkill(skill: SkillDto);
    fun deleteSkill(skillId: Long);
    fun addFavorite(username: String, skillId: Long);
    fun removeFavorite(username: String, skillId: Long);
    fun changeVisibility(skillId: Long, visibility: Long);
}