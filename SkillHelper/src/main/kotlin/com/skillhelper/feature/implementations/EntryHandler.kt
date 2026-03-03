package com.skillhelper.feature.implementations

import com.skillhelper.domain.entities.EntryId
import com.skillhelper.domain.entities.Username
import com.skillhelper.feature.interfaces.IEntryHandler
import com.skillhelper.feature.mappers.toDomain
import com.skillhelper.feature.mappers.toDto
import com.skillhelper.feature.models.EntryDto
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service

@Service
class EntryHandler(
    val entryRepository: IEntryRepository,
    val userRepository: IUserRepository,
): IEntryHandler {
    override fun getEntries(username: Username): List<EntryDto> {
        return entryRepository.getEntries(username).map { it.toDto() }
    }

    override fun getEntryById(id: EntryId): EntryDto? {
        return entryRepository.getEntryById(id)?.toDto()
    }

    override fun addEntry(entryDto: EntryDto): EntryId {
        if(!userRepository.userExists(Username(entryDto.username))) return EntryId( -1);
        return entryRepository.addEntry(entryDto.toDomain())
    }

    override fun updateEntry(entryDto: EntryDto) {
        val entry = entryRepository.getEntryById(EntryId(entryDto.id));
        if(entry != null && entry.user != Username(entryDto.username)) return;
        entryRepository.updateEntry(entryDto.toDomain())
    }

    override fun deleteEntry(id: EntryId) {
        entryRepository.deleteEntry(id)
    }
}