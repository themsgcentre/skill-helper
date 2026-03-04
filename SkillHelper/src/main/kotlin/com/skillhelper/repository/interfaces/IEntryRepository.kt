package com.skillhelper.repository.interfaces

import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.Username

interface IEntryRepository {
    fun getEntries(username: Username): List<Entry>
    fun getEntryById(id: EntryId): Entry?
    fun addEntry(entry: Entry): EntryId
    fun updateEntry(entry: Entry)
    fun deleteEntry(id: EntryId)
    fun entryExists(id: EntryId): Boolean
}