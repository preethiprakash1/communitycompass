package com.example.javadb;

import com.example.javadb.controller.RouteController;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteControllerTest {

    private final RouteController routeController = new RouteController(null);

    @Test
    public void indexTest() {
        String expectedResult = "Hi, welcome to Community Compass";
        assertEquals(expectedResult, routeController.welcome());
    }
    
}