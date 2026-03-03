package com.skillhelper.domain.entities

import java.time.LocalDateTime

@JvmInline
value class EntryId(val value: Long)

data class Entry(
    val id: EntryId? = null,
    val user: Username,
    val time: LocalDateTime,
    val text: String,
    val stressLevel: StressLevel
)