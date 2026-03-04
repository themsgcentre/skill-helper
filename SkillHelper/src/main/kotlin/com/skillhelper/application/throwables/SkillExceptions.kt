package com.skillhelper.application.throwables

class AuthorNotFoundException :
    DomainException("Author not found.")

class SkillNotFoundException :
    DomainException("Skill not found.")

class SkillAccessDeniedException :
    DomainException("Access to skill denied.")