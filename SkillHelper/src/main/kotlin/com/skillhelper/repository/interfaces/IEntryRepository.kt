package com.skillhelper.repository.interfaces

import com.skillhelper.repository.models.EntryDbo

interface IEntryRepository {
    fun getEntries(username: String): List<EntryDbo>
    fun getEntryById(id: Long): EntryDbo?
    fun addEntry(entry: EntryDbo): Long
    fun updateEntry(entry: EntryDbo)
    fun deleteEntry(id: Long)
}