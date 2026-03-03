package com.skillhelper.repository.interfaces

import com.skillhelper.domain.entities.Entry
import com.skillhelper.domain.entities.EntryId
import com.skillhelper.domain.entities.Username

interface IEntryRepository {
    fun getEntries(username: Username): List<Entry>
    fun getEntryById(id: EntryId): Entry?
    fun addEntry(entry: Entry): EntryId
    fun updateEntry(entry: Entry)
    fun deleteEntry(id: EntryId)
}