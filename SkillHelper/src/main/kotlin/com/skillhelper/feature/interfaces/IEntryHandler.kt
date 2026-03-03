package com.skillhelper.feature.interfaces

import com.skillhelper.domain.entities.EntryId
import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.models.EntryDto

interface IEntryHandler {
    fun getEntries(username: Username): List<EntryDto>
    fun getEntryById(id: EntryId): EntryDto?
    fun addEntry(entryDto: EntryDto): EntryId
    fun updateEntry(entryDto: EntryDto)
    fun deleteEntry(id: EntryId)
}