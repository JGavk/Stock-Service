package com.example.stockmicroservice.category.application.domain.exceptions;

import com.example.stockmicroservice.category.domain.exceptions.CategoryAlreadyExistsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryNotFoundExceptionTest {

    @Test
    void exceptionShouldCarryMessage() {

        CategoryAlreadyExistsException exception = new CategoryAlreadyExistsException("Category already exists");

        assertEquals("Category already exists", exception.getMessage());
    }
}
