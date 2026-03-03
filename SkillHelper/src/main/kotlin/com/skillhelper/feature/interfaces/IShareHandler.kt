package com.skillhelper.feature.interfaces

import com.skillhelper.domain.entities.ShareId
import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.models.ShareCreationDto
import com.skillhelper.feature.models.ShareDto

interface IShareHandler {
    fun addShare(share: ShareCreationDto);
    fun readShare(shareId: ShareId);
    fun deleteAllForUser(username: Username);
    fun deleteShare(shareId: ShareId);
    fun getAll(username: Username): List<ShareDto>;
}