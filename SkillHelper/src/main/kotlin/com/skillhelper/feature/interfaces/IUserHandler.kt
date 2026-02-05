package com.skillhelper.feature.interfaces

import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.UserDto

interface IUserHandler {
    fun getProfileByName(username: String): ProfileDto?;
    fun createUser(user: UserDto): Boolean;
    fun deleteUser(username: String): Boolean;
    fun updateBio(username: String, bio: String): Boolean;
    fun updateProfilePicture(username: String, imageSrc: String?): Boolean;
    fun updateUsername(username: String, newName: String): Boolean;
    fun updatePassword(username: String, oldPassword: String, newPassword: String): Boolean;
}