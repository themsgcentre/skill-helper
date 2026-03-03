package com.skillhelper.repository.interfaces

import com.skillhelper.domain.entities.User
import com.skillhelper.domain.entities.Username

interface IUserRepository {
    fun getUserByName(username: Username): User?;
    fun createUser(user: User);
    fun deleteUser(username: Username);
    fun updateBio(username: Username, bio: String);
    fun updateProfilePicture(username: Username, imageSrc: String?);
    fun updateUsername(username: Username, newName: Username);
    fun updatePassword(username: Username, newPassword: String);
    fun getPassword(username: Username): String?;
    fun userExists(username: Username): Boolean;
}