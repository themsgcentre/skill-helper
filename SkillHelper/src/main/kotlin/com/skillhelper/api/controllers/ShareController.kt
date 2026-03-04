package com.skillhelper.api.controllers

import com.skillhelper.api.helpers.toUsername
import com.skillhelper.api.mappers.toDomain
import com.skillhelper.api.mappers.toDto
import com.skillhelper.application.interfaces.IShareHandler
import com.skillhelper.api.models.ShareCreationDto
import com.skillhelper.api.models.ShareDto
import com.skillhelper.application.entities.ShareId
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/share")
class ShareController(val shareHandler: IShareHandler) {
    @PostMapping("/add")
    fun addShare(@RequestBody share: ShareCreationDto) {
        shareHandler.addShare(share.toDomain());
    }

    @PutMapping("/read/{shareId}")
    fun readShare(auth: Authentication, @PathVariable shareId: Long) {
        val username = auth.toUsername()
        shareHandler.readShare(username, ShareId(shareId));
    }

    @DeleteMapping("/deleteAllForUser")
    fun deleteAllForUser(auth: Authentication) {
        val username = auth.toUsername()
        shareHandler.deleteAllForUser(username);
    }

    @GetMapping("/getAll")
    fun getAll(auth: Authentication): List<ShareDto> {
        val username = auth.toUsername();
        return shareHandler.getAll(username).map { it.toDto() };
    }

    @GetMapping("/delete/{shareId}")
    fun getById(auth: Authentication, @PathVariable shareId: Long) {
        val username = auth.toUsername()
        return shareHandler.deleteShare(username, ShareId(shareId));
    }
}