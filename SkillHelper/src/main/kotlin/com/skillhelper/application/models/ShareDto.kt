package com.skillhelper.application.models

import java.util.Date

data class ShareDto(
    val id: Long,
    val from: String,
    val fromProfileImg: String?,
    val skillId: Long,
    val skillImg: String?,
    val dateShared: Date,
)