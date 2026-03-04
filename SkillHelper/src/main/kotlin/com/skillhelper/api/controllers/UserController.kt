package com.skillhelper.api.controllers

import com.skillhelper.api.helpers.BioUpdateHelper
import com.skillhelper.api.helpers.PasswordUpdaterHelper
import com.skillhelper.api.helpers.ProfileImageUpdateHelper
import com.skillhelper.api.helpers.UsernameUpdateHelper
import com.skillhelper.api.helpers.toUsername
import com.skillhelper.api.mappers.toDto
import com.skillhelper.application.interfaces.IUserHandler
import com.skillhelper.api.models.ProfileDto
import com.skillhelper.api.models.UserDto
import com.skillhelper.application.entities.Profile
import com.skillhelper.application.entities.Username
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import org.springframework.security.core.Authentication


@RestController
@RequestMapping("/api/user")
class UserController(val userHandler: IUserHandler) {
    @GetMapping("/getProfileByName/{username}")
    fun getProfileByName(@PathVariable username: String): ProfileDto {
        return userHandler.getProfileByName(Username(username)).toDto();
    }

    @DeleteMapping("/delete")
    fun deleteUser(auth: Authentication) {
        val username = auth.toUsername()
        userHandler.deleteUser(username);
    }

    @PostMapping("/create")
    fun createUser(@RequestBody userDto: UserDto) {
        userHandler.createUser(Username(userDto.username), userDto.password, Profile(userDto.bio, userDto.profileImage));
    }

    @PutMapping("/update/bio")
    fun updateBio(auth: Authentication, @RequestBody updateHelper: BioUpdateHelper) {
        val username = auth.toUsername()
        userHandler.updateBio(username, updateHelper.bio);
    }

    @PutMapping("/update/picture")
    fun updateProfilePicture(auth: Authentication, @RequestBody updateHelper: ProfileImageUpdateHelper) {
        val username = auth.toUsername()
        userHandler.updateProfilePicture(username, updateHelper.imageUrl)
    }

    @PutMapping("/update/")
    fun updateUsername(auth: Authentication, request: HttpServletRequest, @RequestBody updateHelper: UsernameUpdateHelper) {
        val username = auth.toUsername()
        userHandler.updateUsername(username, Username(updateHelper.newUsername))
        request.session.invalidate()
    }

    @PutMapping("/update/password")
    fun updatePassword(auth: Authentication, @RequestBody updateHelper: PasswordUpdaterHelper) {
        val username = auth.toUsername()
        userHandler.updatePassword(username, updateHelper.oldPassword, updateHelper.newPassword)
    }

    @GetMapping("/checkIfUsernameExists({username})")
    fun checkIfUsernameExists(@PathVariable username: String): Boolean {
        return userHandler.userExists(Username(username));
    }
}