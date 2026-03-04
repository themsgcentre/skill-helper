package com.skillhelper.application.throwables

class AuthorNotFoundException :
    DomainException("Author does not exist")

class SkillNotFoundException :
    DomainException("Skill does not exist")