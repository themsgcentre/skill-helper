package com.skillhelper.repository.interfaces

import com.skillhelper.repository.models.VisibilityDbo

interface IVisibilityRepository {
    fun getAllVisibilityLevels(): List<VisibilityDbo>
}