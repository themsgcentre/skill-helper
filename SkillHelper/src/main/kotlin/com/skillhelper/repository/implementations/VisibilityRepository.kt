package com.skillhelper.repository.implementations

import com.skillhelper.repository.interfaces.IVisibilityRepository
import com.skillhelper.repository.models.VisibilityDbo
import org.springframework.stereotype.Service

@Service
class VisibilityRepository: IVisibilityRepository {
    override fun getAllVisibilityLevels(): List<VisibilityDbo> {
        TODO("Not yet implemented")
    }

    override fun getVisibilityString(id: Long): String {
        TODO("Not yet implemented")
    }
}