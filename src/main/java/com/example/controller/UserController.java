package com.example.controller;

import com.example.storage.Storage;

public class UserController {
    private final Storage storage;

    public UserController(Storage storage) {
        this.storage = storage;
    }

    public String registerUser(String username) {
        if (username == null || username.isBlank()) {
            return "ERROR";
        }

        if (storage.exists(username)) {
            return "EXISTS";
        }

        storage.save(username);
        return "OK";
    }
}