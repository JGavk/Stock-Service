package com.example.stockmicroservice.category.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxCharacterExceptionTest {

    @Test
    void testExceptionMessage() {
        MaxCharacterException exception = assertThrows(MaxCharacterException.class, () -> {
            throw new MaxCharacterException();
        });

        assertEquals("Maximum number of characters exceeded", exception.getMessage());
    }
}