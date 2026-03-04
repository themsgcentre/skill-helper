package com.skillhelper.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) }
            .authorizeHttpRequests {
                it.requestMatchers("/api/skill/getVisibilities").permitAll()
                it.requestMatchers("/api/user/create").permitAll()
                it.requestMatchers("/api/auth/**").permitAll() // falls du den hast

                it.requestMatchers("/api/entry/**").authenticated()
                it.requestMatchers("/api/friend/**").authenticated()
                it.requestMatchers("/api/share/**").authenticated()
                it.requestMatchers("/api/skill/**").authenticated()
                it.requestMatchers("/api/user/**").authenticated()

                it.requestMatchers("/api/**").permitAll()
                
                it.anyRequest().authenticated()
            }
        return http.build()
    }
}