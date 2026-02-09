package com.skillhelper.feature.interfaces

import com.skillhelper.feature.models.ShareCreationDto
import com.skillhelper.feature.models.ShareDto

interface IShareHandler {
    fun addShare(share: ShareCreationDto);
    fun readShare(shareId: Long);
    fun deleteAllForUser(username: String);
    fun deleteShare(shareId: Long);
    fun getAll(username: String): List<ShareDto>;
}