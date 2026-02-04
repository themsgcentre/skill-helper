package com.skillhelper.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/getProfileByName/{username}")
    public void GetProfileByName(@PathVariable String username) {

    }

    @DeleteMapping("/delete/{username}")
    public boolean DeleteUser(@PathVariable String username) {
        return false;
    }

    @PostMapping("/create")
    public boolean CreateUser() {
        return false;
    }

    @PutMapping("/update/bio")
    public boolean UpdateBio() {
        return false;
    }

    @PutMapping("/update/picture")
    public boolean UpdateProfilePicture() {
        return false;
    }

    @PutMapping("/update/username")
    public boolean UpdateUsername() {
        return false;
    }

    @PutMapping("/update/password")
    public boolean UpdatePassword() {
        return false;
    }
}
