package com.skillhelper.api;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skill")
public class SkillController {
    @GetMapping("/getById/{id}")
    public String GetById(@PathVariable long id) {
        return "test";
    }

    @GetMapping("/getBySearch")
    public void GetBySearch() {

    }

    @GetMapping("/getByStressLevel")
    public void GetByStressLevel() {

    }

    @PostMapping("/add")
    public void AddSkill() {

    }

    @PutMapping("/edit")
    public void UpdateSkill() {

    }

    @DeleteMapping("/delete")
    public void DeleteSkill() {

    }

    @PostMapping("favorite/add")
    public void AddFavorite() {

    }

    @DeleteMapping("favorite/remove")
    public void RemoveFavorite() {

    }

    @PutMapping("/changeVisibility/{skillId}/{visibilityId}")
    public void ChangeVisibility(@PathVariable String skillId, @PathVariable String visibilityId) {

    }
}
