package com.example.stockmicroservice.category.infrastructure.exceptions;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionResponseTest {
    @Test
    void testExceptionResponseCreation() {
        String message = "Category not found";
        LocalDateTime now = LocalDateTime.now();

        ExceptionResponse response = new ExceptionResponse(message, now);

        assertEquals(message, response.message());
        assertEquals(now, response.timeStamp());
    }
}
