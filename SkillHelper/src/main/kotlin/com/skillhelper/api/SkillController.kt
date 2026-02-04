package com.skillhelper.api

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/skill")
class SkillController {
    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Long): String {
        return "test"
    }

    @GetMapping("/getBySearch")
    fun getBySearch(): String {
        return "not implemented";
    }

    @GetMapping("/getByStressLevel")
    fun getByStressLevel(): String {
            return "not implemented"
    }

    @PostMapping("/add")
    fun addSkill() {
    }

    @PutMapping("/edit")
    fun updateSkill() {

    }

    @DeleteMapping("/delete")
    fun deleteSkill() {

    }

    @PostMapping("favorite/add")
    fun addFavorite() {

    }

    @DeleteMapping("favorite/remove")
    fun removeFavorite() {

    }

    @PutMapping("/changeVisibility/{skillId}/{visibilityId}")
    fun changeVisibility(@PathVariable skillId: String?, @PathVariable visibilityId: String?) {

    }
}