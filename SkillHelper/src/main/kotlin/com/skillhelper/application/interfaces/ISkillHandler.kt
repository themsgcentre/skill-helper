package com.skillhelper.application.interfaces

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Visibility

interface ISkillHandler {
    fun getAllSkills(username: Username) : List<Skill>;
    fun getSkillById(username: Username, id: SkillId) : Skill;
    fun getSkillsBySearch(username: Username, searchString: String) : List<Skill>;
    fun getSkillsByStressLevel(username: Username, minLevel: StressLevel, maxLevel: StressLevel) : List<Skill>;
    fun addSkill(username: Username, skill: Skill): SkillId;
    fun updateSkill(username: Username, skill: Skill);
    fun deleteSkill(username: Username, skillId: SkillId);
    fun addFavorite(username: Username, skillId: SkillId);
    fun removeFavorite(username: Username, skillId: SkillId);
    fun getFavorites(username: Username) : List<Skill>;
    fun changeVisibility(username: Username, skillId: SkillId, visibility: Visibility);
    fun getVisibilities() : List<Visibility>;
}