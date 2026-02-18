package com.skillhelper.feature.interfaces

import com.skillhelper.feature.models.EntryDto

interface IEntryHandler {
    fun getEntries(username: String): List<EntryDto>
    fun getEntryById(id: Long): EntryDto?
    fun addEntry(entryDto: EntryDto): Long
    fun updateEntry(entryDto: EntryDto)
    fun deleteEntry(id: Long)
}