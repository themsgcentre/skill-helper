package com.skillhelper.repository.implementations

import com.skillhelper.repository.interfaces.IFavoriteRepository
import org.springframework.stereotype.Service

@Service
class FavoriteRepository: IFavoriteRepository {
    override fun addFavorite(username: String, skillId: Long) {
        TODO("Not yet implemented")
    }

    override fun removeFavorite(username: String, skillId: Long) {
        TODO("Not yet implemented")
    }
}