package com.skillhelper.application.implementations

import com.skillhelper.domain.entities.Username
import com.skillhelper.application.interfaces.IUserHandler
import com.skillhelper.api.mappers.toProfileDto
import com.skillhelper.application.models.ProfileDto
import com.skillhelper.application.throwables.PasswordNotSetException
import com.skillhelper.application.throwables.PasswordMatchException
import com.skillhelper.application.throwables.UsernameTakenException
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.domain.entities.Profile
import com.skillhelper.domain.entities.User
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserHandler(
    val userRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder
): IUserHandler {
    override fun getProfileByName(username: Username): Profile {
        return userRepository.getUserByName(username)?.profile ?: throw UserNotFoundException(username.value);
    }

    override fun createUser(username: Username, rawPassword: String, profile: Profile) {
        if(userRepository.userExists(username)) throw UsernameTakenException(username.value);
        val hashed = passwordEncoder.encode(rawPassword)
        userRepository.createUser(User(username, hashed, profile))
    }

    override fun deleteUser(username: Username) {
        userRepository.deleteUser(username)
    }

    override fun updateBio(username: Username, bio: String) {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        userRepository.updateBio(username, bio);
    }

    override fun updateProfilePicture(username: Username, imageSrc: String?) {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        userRepository.updateProfilePicture(username, imageSrc);
    }

    override fun updateUsername(oldName: Username, newName: Username) {
        if(!userRepository.userExists(oldName)) throw UserNotFoundException(oldName.value);
        if(userRepository.userExists(newName)) throw UsernameTakenException(newName.value);
        if(oldName == newName) return;
        userRepository.updateUsername(oldName, newName)
    }

    override fun updatePassword(
        username: Username,
        oldPassword: String,
        newPassword: String
    ) {
        if (oldPassword == newPassword) return;
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);

        val storedHash = userRepository.getPassword(username) ?: throw PasswordNotSetException();
        if (passwordEncoder.matches(oldPassword, storedHash)) {
            val newHash = passwordEncoder.encode(newPassword)
            userRepository.updatePassword(username, newHash)
        }

        else throw PasswordMatchException()
    }

    override fun userExists(username: Username): Boolean {
        return userRepository.userExists(username)
    }

    override fun login(username: Username, password: String): Boolean {
        val storedHash = userRepository.getPassword(username) ?: return false
        return passwordEncoder.matches(password, storedHash)
    }
}