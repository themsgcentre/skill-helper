package com.skillhelper.application.interfaces

import com.skillhelper.domain.entities.Share
import com.skillhelper.domain.entities.ShareId
import com.skillhelper.domain.entities.Username

interface IShareHandler {
    fun addShare(share: Share);
    fun readShare(shareId: ShareId);
    fun deleteAllForUser(username: Username);
    fun deleteShare(shareId: ShareId);
    fun getAll(username: Username): List<Share>;
}