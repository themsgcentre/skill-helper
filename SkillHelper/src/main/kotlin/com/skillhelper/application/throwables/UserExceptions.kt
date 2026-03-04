package com.skillhelper.application.throwables

class UsernameTakenException :
    DomainException("Username already taken")

class UserNotFoundException :
    DomainException("User not found")

class PasswordNotSetException :
    DomainException("Password not set")


class PasswordMatchException :
    DomainException("Password does not match")