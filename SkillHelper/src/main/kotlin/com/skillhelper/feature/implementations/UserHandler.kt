package com.skillhelper.feature.implementations

import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.interfaces.IUserHandler
import com.skillhelper.feature.mappers.toDomain
import com.skillhelper.feature.mappers.toProfileDto
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
    override fun getProfileByName(username: Username): ProfileDto? {
        return userRepository.getUserByName(username)?.toProfileDto();
    }

    override fun createUser(user: UserDto) {
        if(userRepository.userExists(Username(user.username))) return;
        val hashed = passwordEncoder.encode(user.password)
        userRepository.createUser(user.toDomain(hashed))
    }

    override fun deleteUser(username: Username) {
        userRepository.deleteUser(username)
    }

    override fun updateBio(username: Username, bio: String) {
        userRepository.updateBio(username, bio);
    }

    override fun updateProfilePicture(username: Username, imageSrc: String?) {
        userRepository.updateProfilePicture(username, imageSrc);
    }

    override fun updateUsername(oldName: Username, newName: Username) {
        if(userRepository.userExists(newName) || oldName == newName) return;
        userRepository.updateUsername(oldName, newName)
    }

    override fun updatePassword(
        username: Username,
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

    override fun userExists(username: Username): Boolean {
        return userRepository.userExists(username)
    }

    override fun login(username: Username, password: String): Boolean {
        val hashed = passwordEncoder.encode(password)
        val storedHash = userRepository.getPassword(username)
        return hashed == storedHash
    }
}