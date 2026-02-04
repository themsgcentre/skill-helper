package com.skillhelper.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    @GetMapping("/getFriends/{username}")
    public void GetFriends(@PathVariable String username) {

    }

    @GetMapping("/getRequests/{username}")
    public void GetRequests(@PathVariable String username) {

    }

    @DeleteMapping("/removeFriend")
    public void RemoveFriend() {

    }

    @PostMapping("/sendRequest")
    public void AddRequest() {

    }

    @PostMapping("/acceptRequest")
    public void AcceptRequest() {

    }

    @DeleteMapping("/denyRequest")
    public void DenyRequest() {

    }
}
