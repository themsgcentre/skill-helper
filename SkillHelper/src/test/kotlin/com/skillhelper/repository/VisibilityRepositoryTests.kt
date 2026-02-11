package com.skillhelper.repository

import com.skillhelper.repository.implementations.VisibilityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class VisibilityRepositoryTests {

    @Autowired
    lateinit var repository: VisibilityRepository

    @Test
    fun getAllVisibilityLevels_ReturnsSeededLevels() {
        val actual = repository.getAllVisibilityLevels()

        assertThat(actual).hasSizeGreaterThanOrEqualTo(3)
        assertThat(actual.map { it.description })
            .contains("Private", "Public", "Friends-Only")
    }
}
