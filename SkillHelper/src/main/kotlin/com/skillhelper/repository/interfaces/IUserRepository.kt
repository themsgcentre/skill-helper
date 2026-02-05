package com.skillhelper.repository.interfaces
import com.skillhelper.repository.models.UserDbo

interface IUserRepository {
    fun getUserByName(username: String): UserDbo?;
    fun createUser(user: UserDbo): Boolean;
    fun deleteUser(username: String): Boolean;
    fun updateBio(username: String, bio: String): Boolean;
    fun updateProfilePicture(username: String, imageSrc: String?): Boolean;
    fun updateUsername(username: String, newName: String): Boolean;
    fun updatePassword(username: String, newPassword: String): Boolean;
    fun checkIfUsernameExists(username: String): Boolean;
    fun getPassword(username: String): String;
}