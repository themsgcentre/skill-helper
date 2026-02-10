package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IUserHandler
import com.skillhelper.feature.models.ProfileDto
import com.skillhelper.feature.models.UserDto
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserHandler(
    val userRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder
): IUserHandler {
    override fun getProfileByName(username: String): ProfileDto? {
        return userRepository.getUserByName(username)?.toProfileDto();
    }

    override fun createUser(user: UserDto) {
        if(userRepository.userExists(user.username)) return;
        val hashed = passwordEncoder.encode(user.password)
        userRepository.createUser(user.toDbo(hashed))
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

    override fun updateUsername(oldName: String, newName: String) {
        if(userRepository.userExists(newName) || oldName == newName) return;
        userRepository.updateUsername(oldName, newName)
    }

    override fun updatePassword(
        username: String,
        oldPassword: String,
        newPassword: String
    ) {
        if (oldPassword == newPassword || !userRepository.userExists(username)) return;
        val storedHash = userRepository.getPassword(username) ?: return
        if (passwordEncoder.matches(oldPassword, storedHash)) {
            val newHash = passwordEncoder.encode(newPassword)
            userRepository.updatePassword(username, newHash)
        }
    }

    override fun userExists(username: String): Boolean {
        return userRepository.userExists(username)
    }
}