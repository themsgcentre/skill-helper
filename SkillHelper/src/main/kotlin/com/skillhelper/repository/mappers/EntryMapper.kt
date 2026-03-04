package com.skillhelper.repository.mappers

import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.repository.models.EntryDbo

fun EntryDbo.toDomain(): Entry =
    Entry(
        id = EntryId(this.id),
        user = Username(this.username),
        time = this.time,
        text = this.text,
        stressLevel = StressLevel(this.stressLevel)
    )