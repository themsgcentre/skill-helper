package com.skillhelper.feature.interfaces

import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.UserDto

interface IUserHandler {
    fun getProfileByName(username: String): ProfileDto?;
    fun createUser(user: UserDto);
    fun deleteUser(username: String);
    fun updateBio(username: String, bio: String);
    fun updateProfilePicture(username: String, imageSrc: String?);
    fun updateUsername(username: String, newName: String);
    fun updatePassword(username: String, oldPassword: String, newPassword: String);
    fun userExists(username: String): Boolean;
}