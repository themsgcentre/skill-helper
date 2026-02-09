package com.skillhelper.repository.models

import java.util.Date

data class ShareDbo(
    val id: Long,
    val forUser: String,
    val fromUser: String,
    val skill: Long,
    val dateShared: Date,
    val read: Boolean
)