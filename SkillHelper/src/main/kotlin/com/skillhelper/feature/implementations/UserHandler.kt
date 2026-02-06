package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IUserHandler
import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.UserDto
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service

@Service
class UserHandler(val userRepository: IUserRepository): IUserHandler {
    override fun getProfileByName(username: String): ProfileDto? {
        return userRepository.getUserByName(username)?.toProfileDto();
    }

    override fun createUser(user: UserDto) {
        if(!userRepository.checkIfUsernameExists(user.username)){
            userRepository.createUser(user.toDbo())
        }
    }

    override fun deleteUser(username: String) {
        userRepository.deleteUser(username)
    }

    override fun updateBio(username: String, bio: String) {
        userRepository.updateBio(username, bio);
    }

    override fun updateProfilePicture(username: String, imageSrc: String?) {
        userRepository.updateProfilePicture(username, imageSrc);
    }

    override fun updateUsername(username: String, newName: String) {
        if(!checkIfUsernameExists(username) && username != newName) {
            userRepository.updateUsername(username, newName);
        }
    }

    override fun updatePassword(
        username: String,
        oldPassword: String,
        newPassword: String
    ) {
        if(userRepository.getPassword(username) == oldPassword && oldPassword != newPassword) {
            userRepository.updatePassword(username, newPassword);
        }
    }

    override fun checkIfUsernameExists(username: String): Boolean {
        return userRepository.checkIfUsernameExists(username);
    }
}