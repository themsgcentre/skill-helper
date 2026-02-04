package com.skillhelper.api

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/friend")
class FriendController {
    @GetMapping("/getFriends/{username}")
    fun getFriends(@PathVariable username: String) {

    }

    @GetMapping("/getRequests/{username}")
    fun getRequests(@PathVariable username: String) {

    }

    @DeleteMapping("/removeFriend")
    fun removeFriend() {
    }


    @PostMapping("/sendRequest")
    fun addRequest() {

    }

    @PostMapping("/acceptRequest")
    fun acceptRequest() {

    }

    @DeleteMapping("/denyRequest")
    fun denyRequest() {

    }
}