package com.skillhelper.repository.implementations

import com.skillhelper.repository.interfaces.IRequestRepository
import org.springframework.stereotype.Service

@Service
class RequestRepository: IRequestRepository {
    override fun addRequest(username: String, request: String) {
        TODO("Not yet implemented")
    }

    override fun removeRequest(username: String, request: String) {
        TODO("Not yet implemented")
    }

    override fun getRequests(username: String): List<String> {
        TODO("Not yet implemented")
    }
}