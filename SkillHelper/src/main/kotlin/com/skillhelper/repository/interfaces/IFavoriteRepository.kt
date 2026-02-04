package com.skillhelper.repository.interfaces

interface IFavoriteRepository {
    fun addFavorite(username: String, skillId: Long)
    fun removeFavorite(username: String, skillId: Long);
}