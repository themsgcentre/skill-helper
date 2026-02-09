package com.skillhelper.feature.interfaces

interface IShareHandler {
    fun addShare();
    fun readShare();
    fun deleteAll(username: String);
    fun getAll(username: String);
    fun getById(username: String);
}