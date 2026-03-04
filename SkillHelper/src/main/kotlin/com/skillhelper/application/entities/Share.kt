package com.skillhelper.application.entities

import java.util.Date

@JvmInline value class ShareId(val value: Long)

data class Share(
    val id: ShareId? = null,
    val forUser: Username,
    val fromUser: Username,
    val skill: SkillId,
    val dateShared: Date,
    private val read: Boolean = false
) {
    fun markAsRead(): Share =
        if (read) this else copy(read = true)

    fun isRead(): Boolean = read
}