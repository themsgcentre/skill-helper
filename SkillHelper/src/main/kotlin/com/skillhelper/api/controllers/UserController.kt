package com.skillhelper.api.controllers

import com.skillhelper.api.helpers.BioUpdateHelper
import com.skillhelper.api.helpers.PasswordUpdaterHelper
import com.skillhelper.api.helpers.ProfileImageUpdateHelper
import com.skillhelper.api.helpers.UsernameUpdateHelper
import com.skillhelper.feature.interfaces.IUserHandler
import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.UserDto
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/user")
class UserController(val userHandler: IUserHandler) {
    @GetMapping("/getProfileByName/{username}")
    fun getProfileByName(@PathVariable username: String): ProfileDto? {
        return userHandler.getProfileByName(username);
    }

    @DeleteMapping("/delete/{username}")
    fun deleteUser(@PathVariable username: String) {
        userHandler.deleteUser(username);
    }

    @PostMapping("/create")
    fun createUser(@RequestBody userDto: UserDto) {
        userHandler.createUser(userDto);
    }

    @PutMapping("/update/bio")
    fun updateBio(@RequestBody updateHelper: BioUpdateHelper) {
        userHandler.updateBio(updateHelper.username, updateHelper.bio);
    }

    @PutMapping("/update/picture")
    fun updateProfilePicture(@RequestBody updateHelper: ProfileImageUpdateHelper) {
        userHandler.updateProfilePicture(updateHelper.username, updateHelper.imageUrl)
    }

    @PutMapping("/update/username")
    fun updateUsername(@RequestBody updateHelper: UsernameUpdateHelper) {
        userHandler.updateUsername(updateHelper.username, updateHelper.newUsername)
    }

    @PutMapping("/update/password")
    fun updatePassword(@RequestBody updateHelper: PasswordUpdaterHelper) {
        userHandler.updatePassword(updateHelper.username, updateHelper.oldPassword, updateHelper.newPassword)
    }

    @GetMapping("/checkIfUsernameExists({username})")
    fun checkIfUsernameExists(@PathVariable username: String): Boolean {
        return userHandler.usernameExists(username);
    }
}