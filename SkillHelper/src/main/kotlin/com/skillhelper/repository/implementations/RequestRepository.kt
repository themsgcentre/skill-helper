package com.skillhelper.repository.implementations

import com.skillhelper.repository.interfaces.IRequestRepository

class RequestRepository: IRequestRepository {
    override fun addRequest(username: String, requestTo: String) {
        TODO("Not yet implemented")
    }

    override fun removeRequest(username: String, requestTo: String) {
        TODO("Not yet implemented")
    }

    override fun getRequests(username: String): List<String> {
        TODO("Not yet implemented")
    }
}