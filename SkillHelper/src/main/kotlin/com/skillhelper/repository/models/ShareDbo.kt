package com.skillhelper.repository.models

class ShareDbo(
    val id: Long,
    val forUser: String,
    val fromUser: String,
    val skillId: Long,
    val read: Boolean
)