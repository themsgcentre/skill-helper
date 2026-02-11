package com.skillhelper.repository.interfaces

import com.skillhelper.feature.models.ShareDto
import com.skillhelper.repository.models.ShareDbo

interface IShareRepository {
    fun deleteShare(shareId: Long);
    fun deleteAllForUser(username: String);
    fun addShare(shareDbo: ShareDbo): Long;
    fun readShare(shareId: Long);
    fun getAllForUser(username: String): List<ShareDbo>;
}