package com.skillhelper.repository.interfaces

interface IRequestRepository {
    fun addRequest(username: String, requestTo: String);
    fun removeRequest(username: String, requestTo: String);
    fun getRequests(username: String): List<String>;
}