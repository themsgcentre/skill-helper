package com.skillhelper.feature.interfaces

import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.UserDto

interface IUserHandler {
    fun getProfileByName(username: Username): ProfileDto?;
    fun createUser(user: UserDto);
    fun deleteUser(username: Username);
    fun updateBio(username: Username, bio: String);
    fun updateProfilePicture(username: Username, imageSrc: String?);
    fun updateUsername(oldName: Username, newName: Username);
    fun updatePassword(username: Username, oldPassword: String, newPassword: String);
    fun userExists(username: Username): Boolean;
    fun login(username: Username, password: String);
}