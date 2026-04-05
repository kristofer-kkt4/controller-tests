package com.example.storage;

public interface Storage {
    void save(String username);
    boolean exists(String username);
}