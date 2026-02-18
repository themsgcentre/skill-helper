package com.skillhelper.repository.models

import java.time.LocalDateTime

data class EntryDbo (
    val id: Long,
    val username: String,
    val time: LocalDateTime,
    val text: String,
    val stressLevel: Int,
)