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
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                    ).permitAll()

                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/user/create").permitAll()
                    .requestMatchers("/api/skill/getVisibilities").permitAll()

                    .requestMatchers("/api/entry/**").authenticated()
                    .requestMatchers("/api/friend/**").authenticated()
                    .requestMatchers("/api/share/**").authenticated()
                    .requestMatchers("/api/skill/**").authenticated()
                    .requestMatchers("/api/user/**").authenticated()

                    .anyRequest().permitAll()
            }

        return http.build()
    }
}