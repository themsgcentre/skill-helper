package com.skillhelper.repository.implementations

import com.skillhelper.repository.database.BaseRepository
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.models.EntryDbo
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class EntryRepository(jdbc: JdbcClient): IEntryRepository, BaseRepository(jdbc, "[Entry]") {
    override fun getEntries(username: String): List<EntryDbo> {
        TODO("Not yet implemented")
    }

    override fun getEntryById(id: Long): EntryDbo? {
        TODO("Not yet implemented")
    }

    override fun addEntry(entry: EntryDbo) {
        TODO("Not yet implemented")
    }

    override fun updateEntry(entry: EntryDbo) {
        TODO("Not yet implemented")
    }

    override fun deleteEntry(id: Long) {
        TODO("Not yet implemented")
    }
}