package com.skillhelper.api

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/share")
class ShareController {
    @PostMapping("/add")
    fun addShare() {
    }

    @PutMapping("/read")
    fun readShare() {
    }

    @DeleteMapping("/deleteMessages/{username}")
    fun deleteMessages(@PathVariable username: String?) {
    }

    @GetMapping("/getAll/{username}")
    fun getAll(@PathVariable username: String?) {
    }

    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Long) {
    }
}