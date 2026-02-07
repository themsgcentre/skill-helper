package com.skillhelper.repository.interfaces

interface IRequestRepository {
    fun addRequest(username: String, request: String);
    fun removeRequest(username: String, request: String);
    fun getRequests(username: String): List<String>;
}