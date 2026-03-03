package com.skillhelper.repository.mappers

import com.skillhelper.domain.entities.Entry
import com.skillhelper.domain.entities.EntryId
import com.skillhelper.domain.entities.StressLevel
import com.skillhelper.domain.entities.Username
import com.skillhelper.repository.models.EntryDbo

fun EntryDbo.toDomain(): Entry =
    Entry(
        id = EntryId(this.id),
        user = Username(this.username),
        time = this.time,
        text = this.text,
        stressLevel = StressLevel(this.stressLevel)
    )