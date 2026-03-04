package com.skillhelper.api.mappers

import com.skillhelper.application.entities.Visibility
import com.skillhelper.api.models.VisibilityDto

fun Visibility.toDto(): VisibilityDto =
    VisibilityDto(
        id = this.id,
        text = this.label
    )