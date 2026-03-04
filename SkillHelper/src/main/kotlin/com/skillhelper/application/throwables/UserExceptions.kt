package com.skillhelper.application.throwables

class UsernameTakenException(
    username: String
) :
    DomainException("Username $username already taken.")

class UserNotFoundException(
    username: String
) : DomainException("User $username not found.")


class PasswordNotSetException :
    DomainException("Password not set.")


class PasswordMatchException :
    DomainException("Password does not match.")