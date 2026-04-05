package com.example.storage;

import java.util.HashSet;
import java.util.Set;

public class MockStorage implements Storage {
    private final Set<String> users = new HashSet<>();

    @Override
    public void save(String username) {
        users.add(username);
    }

    @Override
    public boolean exists(String username) {
        return users.contains(username);
    }
}
