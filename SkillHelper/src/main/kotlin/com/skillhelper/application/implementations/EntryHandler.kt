package com.skillhelper.application.implementations

import com.skillhelper.domain.entities.EntryId
import com.skillhelper.domain.entities.Username
import com.skillhelper.application.interfaces.IEntryHandler
import com.skillhelper.api.mappers.toDomain
import com.skillhelper.api.mappers.toDto
import com.skillhelper.application.models.EntryDto
import com.skillhelper.application.throwables.EntryNotFoundException
import com.skillhelper.application.throwables.InvalidEntryOperationException
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service
import com.skillhelper.domain.entities.Entry

@Service
class EntryHandler(
    val entryRepository: IEntryRepository,
    val userRepository: IUserRepository,
): IEntryHandler {
    override fun getEntries(username: Username): List<Entry> {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        return entryRepository.getEntries(username)
    }

    override fun getEntryById(id: EntryId): Entry {
        return entryRepository.getEntryById(id) ?: throw EntryNotFoundException()
    }

    override fun addEntry(entry: Entry): EntryId {
        if(!userRepository.userExists(entry.user)) throw UserNotFoundException(entry.user.value);
        return entryRepository.addEntry(entry)
    }

    override fun updateEntry(entry: Entry) {
        val originalEntry = entryRepository.getEntryById(entry.id!!) ?: throw EntryNotFoundException();
        if(entry.user != originalEntry.user) throw InvalidEntryOperationException()
        entryRepository.updateEntry(entry)
    }

    override fun deleteEntry(id: EntryId) {
        entryRepository.deleteEntry(id)
    }
}