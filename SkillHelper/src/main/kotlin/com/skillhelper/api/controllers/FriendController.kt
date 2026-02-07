package com.skillhelper.api.controllers

import com.skillhelper.api.helpers.FriendHelper
import com.skillhelper.api.helpers.RequestHelper
import com.skillhelper.api.helpers.UsernameHelper
import com.skillhelper.feature.interfaces.IFriendHandler
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
    fun getFriends(@RequestBody helper: UsernameHelper): List<String> {
        return friendHandler.getFriends(helper.username);
    }

    @GetMapping("/getRequests")
    fun getRequests(@RequestBody helper: UsernameHelper): List<String> {
        return friendHandler.getRequests(helper.username);
    }

    @DeleteMapping("/removeFriend")
    fun removeFriend(@RequestBody helper: FriendHelper) {
        friendHandler.removeFriend(helper.username, helper.friend)
    }


    @PostMapping("/sendRequest")
    fun addRequest(@RequestBody helper: RequestHelper) {
        friendHandler.addRequest(helper.username, helper.request)
    }

    @PostMapping("/acceptRequest")
    fun acceptRequest(@RequestBody helper: RequestHelper) {
        friendHandler.acceptRequest(helper.username, helper.request)
    }

    @DeleteMapping("/denyRequest")
    fun denyRequest(@RequestBody helper: RequestHelper) {
        friendHandler.removeRequest(helper.username, helper.request)
    }
}