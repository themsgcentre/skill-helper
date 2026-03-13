package com.skillhelper.repository.implementations

import com.skillhelper.application.entities.User
import com.skillhelper.application.entities.Username
import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.mappers.toDomain
import com.skillhelper.repository.models.UserDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class UserRepository(
    jdbc: JdbcClient
): IUserRepository, BaseRepository(jdbc, "[User]") {
    override fun getUserByName(username: Username): User? {
        val sql = """
        SELECT * from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
        );

        return query<UserDbo>(sql, params).firstOrNull()?.toDomain();
    }

    override fun createUser(user: User) {
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
            "username" to user.username.value,
            "password" to user.password,
            "profileImage" to user.profile.profileImage,
            "bio" to user.profile.bio
        )

        execute(sql, params);
    }

    override fun deleteUser(username: Username) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
        );

        execute(sql, params);
    }

    override fun updateBio(username: Username, bio: String) {
        val sql = """
        UPDATE dbo.$tableName
        SET Bio = :bio
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "bio" to bio
        );

        execute(sql, params);
    }

    override fun updateProfilePicture(username: Username, imageSrc: String?) {
        val sql = """
        UPDATE dbo.$tableName
        SET ProfileImage = :imageSrc
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "imageSrc" to imageSrc
        );

        execute(sql, params);
    }

    override fun updateUsername(username: Username, newName: Username) {
        val sql = """
        UPDATE dbo.$tableName
        SET Username = :newName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "newName" to newName.value
        );

        execute(sql, params);
    }

    override fun updatePassword(username: Username, newPassword: String) {
        val sql = """
        UPDATE dbo.$tableName
        SET Password = :newPassword
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
            "newPassword" to newPassword
        );

        execute(sql, params);
    }

    override fun getPassword(username: Username): String? {
        val sql = """
        SELECT (Password) from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username.value,
        );

        return query<String>(sql, params).firstOrNull();
    }

    override fun userExists(username: Username): Boolean {
        val sql = """
        SELECT CASE 
            WHEN EXISTS (
                SELECT 1
                FROM dbo.$tableName
                WHERE Username = :username
            )
            THEN 1 ELSE 0
        END
        """.trimIndent()

        val params = mapOf(
            "username" to username.value
        )

        val result = query<Int>(sql, params).first();
        return (result == 1);
    }
}