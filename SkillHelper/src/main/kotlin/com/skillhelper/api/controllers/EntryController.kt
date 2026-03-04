package com.skillhelper.api.controllers

import com.skillhelper.api.helpers.toUsername
import com.skillhelper.api.mappers.toDomain
import com.skillhelper.api.mappers.toDto
import com.skillhelper.application.interfaces.IEntryHandler
import com.skillhelper.api.models.EntryDto
import com.skillhelper.application.entities.EntryId
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/entry")
class EntryController(val entryHandler: IEntryHandler) {
    @GetMapping("/getEntries")
    fun getEntries(auth: Authentication): List<EntryDto> {
        val username = auth.toUsername()
        return entryHandler.getEntries(username).map { it.toDto() };
    }

    @GetMapping("getById/{id}")
    fun getEntryById(auth: Authentication, @PathVariable id: Long): EntryDto {
        val username = auth.toUsername()
        return entryHandler.getEntryById(username, EntryId(id)).toDto()
    }

    @PostMapping("/addEntry")
    fun addEntry(@RequestBody entry: EntryDto) {
        entryHandler.addEntry(entry.toDomain());
    }

    @PutMapping("/updateEntry")
    fun updateEntry(auth: Authentication, @RequestBody entry: EntryDto) {
        val username = auth.toUsername()
        entryHandler.updateEntry(username, entry.toDomain());
    }

    @DeleteMapping("/deleteEntry/{id}")
    fun deleteEntry(auth: Authentication, @PathVariable id: Long) {
        val username = auth.toUsername()
        entryHandler.deleteEntry(username, EntryId(id))
    }
}