package com.skillhelper.application.throwables

class EntryNotFoundException :
    DomainException("Entry not found.")

class InvalidEntryOperationException :
    DomainException("Invalid Operation on entry. Author cannot be changed.")