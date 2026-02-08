package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.models.UserDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class UserRepository(
    jdbc: JdbcClient
): IUserRepository, BaseRepository(jdbc, "[User]") {
    override fun getUserByName(username: String): UserDbo? {
        TODO("Not yet implemented")
    }

    override fun createUser(user: UserDbo) {
        val sql = """
        INSERT INTO dbo.$tableName (
            Username,
            Password,
            ProfileImage,
            Bio
        )
        VALUES (
            :username,
            :password,
            :profileImage,
            :bio
        );
        """.trimIndent()

        val params = mapOf(
            "username" to user.username,
            "password" to user.password,
            "profileImage" to user.profileImage,
            "bio" to user.bio
        )

        execute(sql, params);
    }

    override fun deleteUser(username: String) {
        TODO("Not yet implemented")
    }

    override fun updateBio(username: String, bio: String) {
        TODO("Not yet implemented")
    }

    override fun updateProfilePicture(username: String, imageSrc: String?) {
        TODO("Not yet implemented")
    }

    override fun updateUsername(username: String, newName: String) {
        TODO("Not yet implemented")
    }

    override fun updatePassword(username: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override fun checkIfUsernameExists(username: String): Boolean {
        return false;
    }

    override fun getPassword(username: String): String? {
        TODO("Not yet implemented")
    }
}