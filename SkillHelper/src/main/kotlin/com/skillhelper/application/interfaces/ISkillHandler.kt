package com.skillhelper.application.interfaces

import com.skillhelper.domain.entities.Skill
import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.StressLevel
import com.skillhelper.domain.entities.Username
import com.skillhelper.domain.entities.Visibility

interface ISkillHandler {
    fun getAllSkills() : List<Skill>;
    fun getSkillById(id: SkillId) : Skill?;
    fun getSkillsBySearch(searchString: String) : List<Skill>;
    fun getSkillsByStressLevel(minLevel: StressLevel, maxLevel: StressLevel) : List<Skill>;
    fun addSkill(skill: Skill): SkillId;
    fun updateSkill(skill: Skill);
    fun deleteSkill(skillId: SkillId);
    fun addFavorite(username: Username, skillId: SkillId);
    fun removeFavorite(username: Username, skillId: SkillId);
    fun changeVisibility(skillId: SkillId, visibility: Visibility);
    fun getVisibilities() : List<Visibility>;
}