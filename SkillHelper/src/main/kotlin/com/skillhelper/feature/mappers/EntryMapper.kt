package com.skillhelper.feature.mappers

import com.skillhelper.domain.entities.Entry
import com.skillhelper.domain.entities.EntryId
import com.skillhelper.domain.entities.StressLevel
import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.models.EntryDto

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