package com.skillhelper.api.controllers

import com.skillhelper.api.helpers.FavoriteHelper
import com.skillhelper.api.helpers.SearchStringHelper
import com.skillhelper.api.helpers.StressLevelHelper
import com.skillhelper.api.helpers.toUsername
import com.skillhelper.api.mappers.toDomain
import com.skillhelper.api.mappers.toDto
import com.skillhelper.application.interfaces.ISkillHandler
import com.skillhelper.api.models.SkillDto
import com.skillhelper.api.models.VisibilityDto
import com.skillhelper.application.entities.SkillId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Visibility
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/skill")
class SkillController(val skillHandler: ISkillHandler) {
    @GetMapping("/getById/{id}")
    fun getById(auth: Authentication, @PathVariable id: Long): SkillDto? {
        val username = auth.toUsername();
        return skillHandler.getSkillById(username, SkillId(id)).toDto();
    }

    @GetMapping("/getAllSkills")
    fun getAll(auth: Authentication): List<SkillDto> {
        val username = auth.toUsername();
        return skillHandler.getAllSkills(username).map { it.toDto() };
    }

    @PostMapping("/getBySearch")
    fun getBySearch(auth: Authentication, @RequestBody searchStringHelper: SearchStringHelper): List<SkillDto> {
        val username = auth.toUsername();
        return skillHandler.getSkillsBySearch(username, searchStringHelper.searchString).map { it.toDto() };
    }

    @PostMapping("/getByStressLevel")
    fun getByStressLevel(auth: Authentication, @RequestBody stressLevelHelper: StressLevelHelper): List<SkillDto> {
        val username = auth.toUsername();
        return skillHandler.getSkillsByStressLevel(username, StressLevel(stressLevelHelper.minLevel), StressLevel(stressLevelHelper.maxLevel)).map { it.toDto() };
    }

    @PostMapping("/add")
    fun addSkill(auth: Authentication, @RequestBody skillDto: SkillDto) {
        val username = auth.toUsername();
        skillHandler.addSkill(username, skillDto.toDomain());
    }

    @PutMapping("/edit")
    fun updateSkill(auth: Authentication, @RequestBody skillDto: SkillDto) {
        val username = auth.toUsername();
        skillHandler.updateSkill(username, skillDto.toDomain());
    }

    @DeleteMapping("/delete/{id}")
    fun deleteSkill(auth: Authentication, @PathVariable id: Long) {
        val username = auth.toUsername();
        skillHandler.deleteSkill(username, SkillId(id));
    }

    @PostMapping("favorite/add/")
    fun addFavorite(auth: Authentication, @RequestBody favoriteHelper: FavoriteHelper) {
        val username = auth.toUsername();
        skillHandler.addFavorite(username, SkillId(favoriteHelper.skillId))
    }

    @DeleteMapping("favorite/remove")
    fun removeFavorite(auth: Authentication, @RequestBody favoriteHelper: FavoriteHelper) {
        val username = auth.toUsername();
        skillHandler.removeFavorite(username, SkillId(favoriteHelper.skillId));
    }

    @PutMapping("/changeVisibility/{skillId}/{visibilityId}")
    fun changeVisibility(auth: Authentication, @PathVariable skillId: Long, @PathVariable visibilityId: Long) {
        val username = auth.toUsername();
        skillHandler.changeVisibility(username, SkillId(skillId), Visibility.fromId(visibilityId))
    }

    @GetMapping("/getVisibilities")
    fun getVisibilities(): List<VisibilityDto> {
        return skillHandler.getVisibilities().map { it.toDto() };
    }
}