package com.skillhelper.api.controllers

import com.skillhelper.feature.interfaces.IShareHandler
import com.skillhelper.feature.models.ShareCreationDto
import com.skillhelper.feature.models.ShareDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/share")
class ShareController(val shareHandler: IShareHandler) {
    @PostMapping("/add")
    fun addShare(@RequestBody share: ShareCreationDto) {
        shareHandler.addShare(share);
    }

    @PutMapping("/read/{shareId}")
    fun readShare(@PathVariable shareId: Long) {
        shareHandler.readShare(shareId);
    }

    @DeleteMapping("/deleteAllForUser/{username}")
    fun deleteAllForUser(@PathVariable username: String) {
        shareHandler.deleteAllForUser(username);
    }

    @GetMapping("/getAll/{username}")
    fun getAll(@PathVariable username: String): List<ShareDto> {
        return shareHandler.getAll(username);
    }

    @GetMapping("/delete/{shareId}")
    fun getById(@PathVariable shareId: Long) {
        return shareHandler.deleteShare(shareId);
    }
}