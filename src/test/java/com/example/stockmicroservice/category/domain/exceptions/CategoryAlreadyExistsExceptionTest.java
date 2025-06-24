package com.example.stockmicroservice.category.domain.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryAlreadyExistsExceptionTest {

    @Test
    void testExceptionMessage() {
        // Arrange
        String expectedMessage = "Category already exists";

        // Act
        CategoryAlreadyExistsException exception = new CategoryAlreadyExistsException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}