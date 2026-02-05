package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IUserHandler
import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.UserDto
import org.springframework.stereotype.Service

@Service
class UserHandler: IUserHandler {
    override fun getProfileByName(username: String): ProfileDto? {
        TODO("Not yet implemented")
    }

    override fun createUser(user: UserDto): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteUser(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateBio(username: String, bio: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateProfilePicture(username: String, imageSrc: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateUsername(username: String, newName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updatePassword(
        username: String,
        oldPassword: String,
        newPassword: String
    ): Boolean {
        TODO("Not yet implemented")
    }
}