package com.skillhelper.application.interfaces

import com.skillhelper.application.entities.Username
import com.skillhelper.application.entities.Profile

interface IUserHandler {
    fun getProfileByName(username: Username): Profile;
    fun createUser(username: Username, rawPassword: String, profile: Profile);
    fun deleteUser(username: Username);
    fun updateBio(username: Username, bio: String);
    fun updateProfilePicture(username: Username, imageSrc: String?);
    fun updateUsername(oldName: Username, newName: Username);
    fun updatePassword(username: Username, oldPassword: String, newPassword: String);
    fun userExists(username: Username): Boolean;
}