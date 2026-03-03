package com.skillhelper.application.models

import java.time.LocalDateTime

data class EntryDto (
    val id: Long,
    val username: String,
    val time: LocalDateTime,
    val text: String,
    val stressLevel: Int,
)