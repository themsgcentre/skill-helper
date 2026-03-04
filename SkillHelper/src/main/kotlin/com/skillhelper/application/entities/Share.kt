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
    fun isRead(): Boolean = read
}