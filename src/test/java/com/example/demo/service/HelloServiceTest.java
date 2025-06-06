package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HelloServiceTest {

    private HelloService service;

    @BeforeEach
    void setUp() {
        service = new HelloService();
    }

    @Test
    void sayHello_ShouldReturnCorrectMessage() {
        assertEquals("Hello from Service", service.sayHello());
    }
}