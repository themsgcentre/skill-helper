package com.skillhelper.feature.models

import java.util.Date

class ShareCreationDto(
    val from: String,
    val to: String,
    val skillId: String,
    val dateShared: Date,
)