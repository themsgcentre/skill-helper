package com.skillhelper.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/share")
public class ShareController {
    @PostMapping("/add")
    public void AddShare() {

    }

    @PutMapping("/read")
    public void ReadShare() {

    }

    @DeleteMapping("/deleteMessages/{username}")
    public void DeleteMessages(@PathVariable String username) {

    }

    @GetMapping("/getAll/{username}")
    public void GetAll(@PathVariable String username) {

    }

    @GetMapping("/getById/{id}")
    public void GetById(@PathVariable long id) {
        
    }
}
