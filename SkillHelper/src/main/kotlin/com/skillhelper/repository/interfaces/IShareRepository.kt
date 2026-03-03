package com.skillhelper.repository.interfaces

import com.skillhelper.domain.entities.Share
import com.skillhelper.domain.entities.ShareId
import com.skillhelper.domain.entities.Username

interface IShareRepository {
    fun deleteShare(shareId: ShareId);
    fun deleteAllForUser(username: Username);
    fun addShare(share: Share): ShareId;
    fun readShare(shareId: ShareId);
    fun getAllForUser(username: Username): List<Share>;
}