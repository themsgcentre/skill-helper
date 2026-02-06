package com.skillhelper.repository.implementations

import com.skillhelper.repository.interfaces.IUserRepository
import com.skillhelper.repository.models.UserDbo
import org.springframework.stereotype.Service

@Service
class UserRepository: IUserRepository {
    override fun getUserByName(username: String): UserDbo? {
        TODO("Not yet implemented")
    }

    override fun createUser(user: UserDbo) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun getPassword(username: String): String? {
        TODO("Not yet implemented")
    }
}