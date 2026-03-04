package com.skillhelper.repository.interfaces

import com.skillhelper.application.entities.Username

interface IRequestRepository {
    fun addRequest(username: Username, request: Username);
    fun removeRequest(username: Username, request: Username);
    fun getRequests(username: Username): List<Username>;
}