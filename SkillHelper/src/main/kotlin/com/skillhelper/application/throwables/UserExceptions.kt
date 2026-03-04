package com.skillhelper.application.throwables

class UsernameTakenException :
    DomainException("Username already taken.")

class UserNotFoundException(
    message: String = "User not found."
) : DomainException(message)


class PasswordNotSetException :
    DomainException("Password not set.")


class PasswordMatchException :
    DomainException("Password does not match.")