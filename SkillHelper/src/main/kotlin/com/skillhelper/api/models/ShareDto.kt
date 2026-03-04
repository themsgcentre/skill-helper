package com.skillhelper.api.models

import java.util.Date

data class ShareDto(
    val id: Long,
    val from: String,
    val skillId: Long,
    val dateShared: Date,
)