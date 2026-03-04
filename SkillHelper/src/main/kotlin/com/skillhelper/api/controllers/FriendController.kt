package com.skillhelper.api.controllers

import com.skillhelper.api.helpers.FriendHelper
import com.skillhelper.api.helpers.RequestHelper
import com.skillhelper.api.helpers.toUsername
import com.skillhelper.api.mappers.toDto
import com.skillhelper.application.interfaces.IFriendHandler
import com.skillhelper.api.models.FriendDto
import com.skillhelper.api.models.RequestDto
import com.skillhelper.application.entities.Username
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/friend")
class FriendController(val friendHandler: IFriendHandler) {
    @GetMapping("/getFriends")
    fun getFriends(auth: Authentication): List<FriendDto> {
        val username = auth.toUsername()
        return friendHandler.getFriends(username).map { it.toDto() };
    }

    @GetMapping("/getRequests")
    fun getRequests(auth: Authentication): List<RequestDto> {
        val username = auth.toUsername()
        return friendHandler.getRequests(username).map { it.toDto() };
    }

    @DeleteMapping("/removeFriend")
    fun removeFriend(auth: Authentication, @RequestBody helper: FriendHelper) {
        val username = auth.toUsername()
        friendHandler.removeFriend(username, Username(helper.friend))
    }


    @PostMapping("/sendRequest")
    fun addRequest(auth: Authentication, @RequestBody helper: RequestHelper) {
        val username = auth.toUsername()
        friendHandler.addRequest(Username(helper.request), username)
    }

    @PostMapping("/acceptRequest")
    fun acceptRequest(auth: Authentication, @RequestBody helper: RequestHelper) {
        val username = auth.toUsername()
        friendHandler.acceptRequest(username, Username(helper.request))
    }

    @DeleteMapping("/denyRequest")
    fun denyRequest(auth: Authentication, @RequestBody helper: RequestHelper) {
        val username = auth.toUsername()
        friendHandler.removeRequest(username, Username(helper.request))
    }
}