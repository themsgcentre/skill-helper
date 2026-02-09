package com.skillhelper.repository.interfaces
import com.skillhelper.repository.models.UserDbo

interface IUserRepository {
    fun getUserByName(username: String): UserDbo?;
    fun createUser(user: UserDbo);
    fun deleteUser(username: String);
    fun updateBio(username: String, bio: String);
    fun updateProfilePicture(username: String, imageSrc: String?);
    fun updateUsername(username: String, newName: String);
    fun updatePassword(username: String, newPassword: String);
    fun getPassword(username: String): String?;
    fun userExists(username: String): Boolean;
}