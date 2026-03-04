package com.skillhelper.application.implementations

import com.skillhelper.application.entities.EntryId
import com.skillhelper.application.entities.Username
import com.skillhelper.application.interfaces.IEntryHandler
import com.skillhelper.application.throwables.EntryNotFoundException
import com.skillhelper.application.throwables.InvalidEntryOperationException
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.repository.interfaces.IEntryRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service
import com.skillhelper.application.entities.Entry
import com.skillhelper.application.throwables.EntryAccessDeniedException

@Service
class EntryHandler(
    val entryRepository: IEntryRepository,
    val userRepository: IUserRepository,
): IEntryHandler {
    override fun getEntries(username: Username): List<Entry> {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        return entryRepository.getEntries(username)
    }

    override fun getEntryById(username: Username, id: EntryId): Entry {
        val entry = entryRepository.getEntryById(id) ?: throw EntryNotFoundException()
        if(entry.user != username) throw EntryAccessDeniedException();
        return entry;
    }

    override fun addEntry(entry: Entry): EntryId {
        if(!userRepository.userExists(entry.user)) throw UserNotFoundException(entry.user.value);
        return entryRepository.addEntry(entry)
    }

    override fun updateEntry(username: Username, entry: Entry) {
        val originalEntry = entryRepository.getEntryById(entry.id!!) ?: throw EntryNotFoundException();
        if(entry.user != username) throw EntryAccessDeniedException();
        if(entry.user != originalEntry.user) throw InvalidEntryOperationException()
        entryRepository.updateEntry(entry)
    }

    override fun deleteEntry(username: Username, id: EntryId) {
        val entry = entryRepository.getEntryById(id) ?: throw EntryNotFoundException()
        if(entry.user != username) throw EntryAccessDeniedException();
        entryRepository.deleteEntry(id)
    }
}