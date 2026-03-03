package com.skillhelper.feature.interfaces

import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.StressLevel
import com.skillhelper.domain.entities.Username
import com.skillhelper.domain.entities.Visibility
import com.skillhelper.feature.models.SkillDto
import com.skillhelper.feature.models.VisibilityDto

interface ISkillHandler {
    fun getAllSkills() : List<SkillDto>;
    fun getSkillById(id: SkillId) : SkillDto?;
    fun getSkillsBySearch(searchString: String) : List<SkillDto>;
    fun getSkillsByStressLevel(minLevel: StressLevel, maxLevel: StressLevel) : List<SkillDto>;
    fun addSkill(skill: SkillDto): SkillId;
    fun updateSkill(skill: SkillDto);
    fun deleteSkill(skillId: SkillId);
    fun addFavorite(username: Username, skillId: SkillId);
    fun removeFavorite(username: Username, skillId: SkillId);
    fun changeVisibility(skillId: SkillId, visibility: Visibility);
    fun getVisibilities() : List<VisibilityDto>;
}