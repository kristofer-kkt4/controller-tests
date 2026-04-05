package com.example.controller;

import com.example.storage.MockStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    private MockStorage storage;
    private UserController controller;

    @BeforeEach
    void setUp() {
        storage = new MockStorage();
        controller = new UserController(storage);
    }

    @Test
    void testNewUser() {
        assertEquals("OK", controller.registerUser("anna"));
    }

    @Test
    void testExistingUser() {
        storage.save("anna");
        assertEquals("EXISTS", controller.registerUser("anna"));
    }

    @Test
    void testEmptyUser() {
        assertEquals("ERROR", controller.registerUser(""));
    }
}