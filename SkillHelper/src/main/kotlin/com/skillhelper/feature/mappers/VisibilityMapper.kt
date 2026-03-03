package com.skillhelper.feature.mappers

import com.skillhelper.domain.entities.Visibility
import com.skillhelper.feature.models.VisibilityDto

fun Visibility.toDto(): VisibilityDto =
    VisibilityDto(
        id = this.id,
        text = this.label
    )