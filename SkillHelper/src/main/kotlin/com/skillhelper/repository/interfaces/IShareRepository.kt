package com.skillhelper.repository.interfaces

import com.skillhelper.application.entities.Share
import com.skillhelper.application.entities.ShareId
import com.skillhelper.application.entities.Username

interface IShareRepository {
    fun deleteShare(shareId: ShareId);
    fun deleteAllForUser(username: Username);
    fun addShare(share: Share): ShareId;
    fun readShare(shareId: ShareId);
    fun getAllForUser(username: Username): List<Share>;
    fun shareExists(shareId: ShareId): Boolean;
    fun getShareById(shareId: ShareId): Share?;
}