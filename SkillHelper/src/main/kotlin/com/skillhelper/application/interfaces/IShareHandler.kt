package com.skillhelper.application.interfaces

import com.skillhelper.domain.entities.ShareId
import com.skillhelper.domain.entities.Username
import com.skillhelper.application.models.ShareCreationDto
import com.skillhelper.application.models.ShareDto

interface IShareHandler {
    fun addShare(share: ShareCreationDto);
    fun readShare(shareId: ShareId);
    fun deleteAllForUser(username: Username);
    fun deleteShare(shareId: ShareId);
    fun getAll(username: Username): List<ShareDto>;
}