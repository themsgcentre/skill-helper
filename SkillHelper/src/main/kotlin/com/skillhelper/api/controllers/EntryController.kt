package com.skillhelper.api.controllers

import com.skillhelper.feature.interfaces.IEntryHandler
import com.skillhelper.feature.models.EntryDto
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
    @GetMapping("/getEntries/{username}")
    fun getEntries(@PathVariable username: String): List<EntryDto> {
        return entryHandler.getEntries(username);
    }

    @GetMapping("getById/{id}")
    fun getEntryById(@PathVariable id: Long): EntryDto? {
        return entryHandler.getEntryById(id)
    }

    @PostMapping("/addEntry")
    fun addEntry(@RequestBody entry: EntryDto) {
        entryHandler.addEntry(entry);
    }

    @PutMapping("/updateEntry")
    fun updateEntry(@RequestBody entry: EntryDto) {
        entryHandler.updateEntry(entry);
    }

    @DeleteMapping("/deleteEntry/{id}")
    fun deleteEntry(@PathVariable id: Long) {
        entryHandler.deleteEntry(id)
    }
}