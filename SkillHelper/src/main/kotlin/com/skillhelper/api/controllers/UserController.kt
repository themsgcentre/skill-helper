package com.skillhelper.api.controllers

import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/user")
class UserController {
    @GetMapping("/getProfileByName/{username}")
    fun getProfileByName(@PathVariable username: String?) {
    }

    @DeleteMapping("/delete/{username}")
    fun deleteUser(@PathVariable username: String?): Boolean {
        return false
    }

    @PostMapping("/create")
    fun createUser(): Boolean {
        return false
    }

    @PutMapping("/update/bio")
    fun updateBio(): Boolean {
        return false
    }

    @PutMapping("/update/picture")
    fun updateProfilePicture(): Boolean {
        return false
    }

    @PutMapping("/update/username")
    fun updateUsername(): Boolean {
        return false
    }

    @PutMapping("/update/password")
    fun updatePassword(): Boolean {
        return false
    }
}