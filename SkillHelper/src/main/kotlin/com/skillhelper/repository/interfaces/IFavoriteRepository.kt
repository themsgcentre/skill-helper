package com.skillhelper.repository.interfaces

import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.Username

interface IFavoriteRepository {
    fun addFavorite(username: Username, skillId: SkillId)
    fun removeFavorite(username: Username, skillId: SkillId);
    fun getFavorites(username: Username): List<SkillId>;
}