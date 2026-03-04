package com.skillhelper.application.entities

enum class Visibility(val id: Long, val label: String) {
    PRIVATE(1, "Private"),
    PUBLIC(2, "Public"),
    FRIENDS_ONLY(3, "Friends-Only");

    companion object {
        fun fromId(id: Long): Visibility =
            entries.firstOrNull { it.id == id }
                ?: error("Unknown visibility id: $id")
    }
}