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
        val sql = """
        SELECT * from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        return query<UserDbo>(sql, params).firstOrNull();
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

        insert(sql, params);
    }

    override fun deleteUser(username: String) {
        val sql = """
        DELETE from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        execute(sql, params);
    }

    override fun updateBio(username: String, bio: String) {
        val sql = """
        UPDATE dbo.$tableName
        SET Bio = :bio
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "bio" to bio
        );

        execute(sql, params);
    }

    override fun updateProfilePicture(username: String, imageSrc: String?) {
        val sql = """
        UPDATE dbo.$tableName
        SET ProfileImage = :imageSrc
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "imageSrc" to imageSrc
        );

        execute(sql, params);
    }

    override fun updateUsername(username: String, newName: String) {
        val sql = """
        UPDATE dbo.$tableName
        SET Username = :newName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "newName" to newName
        );

        execute(sql, params);
    }

    override fun updatePassword(username: String, newPassword: String) {
        val sql = """
        UPDATE dbo.$tableName
        SET Password = :newPassword
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
            "newPassword" to newPassword
        );

        execute(sql, params);
    }

    override fun getPassword(username: String): String? {
        val sql = """
        SELECT (Password) from dbo.$tableName
        WHERE Username = :username;
        """.trimIndent();

        val params = mapOf(
            "username" to username,
        );

        return query<String>(sql, params).firstOrNull();
    }

    override fun userExists(username: String): Boolean {
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
            "username" to username
        )

        val result = query<Int>(sql, params).first();
        return (result == 1);
    }
}