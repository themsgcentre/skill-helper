package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IEntryHandler
import com.skillhelper.feature.models.EntryDto
import org.springframework.stereotype.Service

@Service
class EntryHandler: IEntryHandler {
    override fun getEntries(username: String): List<EntryDto> {
        TODO("Not yet implemented")
    }

    override fun getEntryById(id: Long): EntryDto? {
        TODO("Not yet implemented")
    }

    override fun addEntry(entryDto: EntryDto) {
        TODO("Not yet implemented")
    }

    override fun updateEntry(entryDto: EntryDto) {
        TODO("Not yet implemented")
    }

    override fun deleteEntry(id: Long) {
        TODO("Not yet implemented")
    }
}