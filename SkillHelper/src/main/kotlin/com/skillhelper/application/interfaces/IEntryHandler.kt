package com.skillhelper.application.interfaces

import com.skillhelper.application.entities.Entry
import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.Username

interface IEntryHandler {
    fun getEntries(username: Username): List<Entry>
    fun getEntryById(username: Username, id: EntryId): Entry
    fun addEntry(entry: Entry): EntryId
    fun updateEntry(username: Username, entry: Entry)
    fun deleteEntry(username: Username, id: EntryId)
}