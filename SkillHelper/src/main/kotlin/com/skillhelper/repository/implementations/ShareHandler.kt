package com.skillhelper.repository.implementations

import com.skillhelper.feature.interfaces.IShareHandler
import com.skillhelper.feature.models.ShareCreationDto
import com.skillhelper.feature.models.ShareDto
import org.springframework.stereotype.Service

@Service
class ShareHandler: IShareHandler {
    override fun addShare(share: ShareCreationDto) {
        TODO("Not yet implemented")
    }

    override fun readShare(shareId: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(username: String) {
        TODO("Not yet implemented")
    }

    override fun deleteShare(shareId: Long) {
        TODO("Not yet implemented")
    }

    override fun getAll(username: String): List<ShareDto> {
        TODO("Not yet implemented")
    }
}