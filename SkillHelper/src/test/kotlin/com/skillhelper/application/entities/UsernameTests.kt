package com.skillhelper.application.entities

import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UsernameTests {

    @Test
    fun validUsername_accepted() {
        assertThatCode { Username("valid_user-1") }.doesNotThrowAnyException()
    }

    @ParameterizedTest
    @ValueSource(strings = ["/", "*", "&", "%", "\"", "'", "=", "$", " ", "a b", "\t", "\n"])
    fun invalidCharacter_rejected(fragment: String) {
        assertThatThrownBy { Username("x${fragment}x") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("Username must not contain")
    }
}
