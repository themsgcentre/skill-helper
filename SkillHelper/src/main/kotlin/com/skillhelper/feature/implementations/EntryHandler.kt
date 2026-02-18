package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IEntryHandler
import com.skillhelper.feature.models.EntryDto
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service

@Service
class EntryHandler(
    val entryRepository: IEntryRepository,
    val userRepository: IUserRepository,
): IEntryHandler {
    override fun getEntries(username: String): List<EntryDto> {
        return entryRepository.getEntries(username).map { it.toDto() }
    }

    override fun getEntryById(id: Long): EntryDto? {
        return entryRepository.getEntryById(id)?.toDto()
    }

    override fun addEntry(entryDto: EntryDto) {
        if(!userRepository.userExists(entryDto.username)) return;
        entryRepository.addEntry(entryDto.toDbo())
    }

    override fun updateEntry(entryDto: EntryDto) {
        entryRepository.updateEntry(entryDto.toDbo())
    }

    override fun deleteEntry(id: Long) {
        entryRepository.deleteEntry(id)
    }
}