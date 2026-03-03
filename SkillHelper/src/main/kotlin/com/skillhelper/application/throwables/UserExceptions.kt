package com.skillhelper.application.throwables

class UserAlreadyExistsException :
    DomainException("User already exists")

class UserNotFoundException :
    DomainException("User not found")

class InvalidPasswordException :
    DomainException("Invalid password")
