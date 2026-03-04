package com.skillhelper.application.throwables

class ShareNotFoundException :
    DomainException("Share not found.")

class ShareAccessDeniedException :
    DomainException("Share Access denied.")