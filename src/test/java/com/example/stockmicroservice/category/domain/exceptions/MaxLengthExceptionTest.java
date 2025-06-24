package com.example.stockmicroservice.category.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MaxLengthExceptionTest {

    @Test
    void testThrowsMaxLengthException() {
        MaxLengthException exception = assertThrows(MaxLengthException.class, () -> {
            throw new MaxLengthException();
        });

        assertNull(exception.getMessage()); // porque no se define ningún mensaje
    }
}