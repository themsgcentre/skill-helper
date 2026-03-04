package com.skillhelper.api.mappers

import com.skillhelper.domain.entities.Visibility
import com.skillhelper.application.models.VisibilityDto

fun Visibility.toDto(): VisibilityDto =
    VisibilityDto(
        id = this.id,
        text = this.label
    )