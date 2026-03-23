package com.skillhelper.api.controllers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager
) {
    data class LoginRequest(val username: String, val password: String)

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest, request: HttpServletRequest) {
        val auth = UsernamePasswordAuthenticationToken(req.username, req.password)
        val result = authenticationManager.authenticate(auth)

        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = result
        SecurityContextHolder.setContext(context)

        val session: HttpSession = request.getSession(true)
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            context
        )
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest) {
        request.logout()
    }
}