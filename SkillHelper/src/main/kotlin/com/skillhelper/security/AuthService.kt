package com.skillhelper.security

import com.skillhelper.application.entities.Username
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: IUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val hash = userRepository.getPassword(Username(username))
            ?: throw UsernameNotFoundException(username)

        return User.withUsername(username)
            .password(hash)
            .roles("USER")
            .build()
    }
}