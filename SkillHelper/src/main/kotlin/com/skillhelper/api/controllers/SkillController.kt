package com.skillhelper.api.controllers

import com.skillhelper.api.helpers.FavoriteHelper
import com.skillhelper.api.helpers.SearchStringHelper
import com.skillhelper.api.helpers.StressLevelHelper
import com.skillhelper.feature.interfaces.ISkillHandler
import com.skillhelper.feature.models.SkillDto
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
    @GetMapping("/test")
    fun test(): String {
        return "string";
    }

    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Long): SkillDto? {
        return skillHandler.getSkillById(id);
    }

    @GetMapping("/getAllSkills")
    fun getAll(): List<SkillDto> {
        return skillHandler.getAllSkills();
    }

    @PostMapping("/getBySearch")
    fun getBySearch(@RequestBody searchStringHelper: SearchStringHelper): List<SkillDto> {
        return skillHandler.getSkillsBySearch(searchStringHelper.searchString)
    }

    @GetMapping("/getByStressLevel")
    fun getByStressLevel(@RequestBody stressLevelHelper: StressLevelHelper): List<SkillDto> {
        return skillHandler.getSkillsByStressLevel(stressLevelHelper.minLevel, stressLevelHelper.maxLevel)
    }

    @PostMapping("/add")
    fun addSkill(@RequestBody skillDto: SkillDto) {
        skillHandler.addSkill(skillDto);
    }

    @PutMapping("/edit")
    fun updateSkill(@RequestBody skillDto: SkillDto) {
        skillHandler.updateSkill(skillDto);
    }

    @DeleteMapping("/delete/{id}")
    fun deleteSkill(@PathVariable id: Long) {
        skillHandler.deleteSkill(id)
    }

    @PostMapping("favorite/add/")
    fun addFavorite(@RequestBody favoriteHelper: FavoriteHelper) {
        skillHandler.addFavorite(favoriteHelper.username, favoriteHelper.skillId)
    }

    @DeleteMapping("favorite/remove")
    fun removeFavorite(@RequestBody favoriteHelper: FavoriteHelper) {
        skillHandler.removeFavorite(favoriteHelper.username, favoriteHelper.skillId)
    }

    @PutMapping("/changeVisibility/{skillId}/{visibilityId}")
    fun changeVisibility(@PathVariable skillId: Long, @PathVariable visibilityId: Long) {
        skillHandler.changeVisibility(skillId, visibilityId)
    }
}