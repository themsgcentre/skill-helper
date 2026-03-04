package com.skillhelper.api.mappers

import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.StressLevel
import com.skillhelper.application.entities.Username
import com.skillhelper.api.models.EntryDto

fun Entry.toDto(): EntryDto =
    EntryDto(
        id = this.id?.value!!,
        username = this.user.value,
        time = this.time,
        text = this.text,
        stressLevel = this.stressLevel.value
    )

fun EntryDto.toDomain(): Entry =
    Entry(
        id = EntryId(id),
        user = Username(this.username),
        time = this.time,
        text = this.text,
        stressLevel = StressLevel(this.stressLevel)
    )